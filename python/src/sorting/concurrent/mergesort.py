# -*- encoding: utf-8 -*-
#
# (c) Copyright 2016 Hewlett Packard Enterprise Development LP
# Copyright 2016 Universidade Federal de Campina Grande
#
#    Licensed under the Apache License, Version 2.0 (the "License"); you may
#    not use this file except in compliance with the License. You may obtain
#    a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
#    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
#    License for the specific language governing permissions and limitations
#    under the License.

from sorting.concurrent import _pickle_method
from sorting.concurrent import available_cpu_count
from sorting.sequential.mergesort import Mergesort

import math
import types
import copy_reg
import multiprocessing


class ConcurrentMergesort(Mergesort):

    def __init__(self):
        super(ConcurrentMergesort, self).__init__()

    def sort(self, array, left=None, right=None):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        if left == right:
            return

        mid = (right + 1 - left) / 2

        l_arr = array[left:left + mid]
        r_arr = array[left + mid:right + 1]

        l_thread = multiprocessing.Process(target=self.sort, args=(l_arr, ))
        r_thread = multiprocessing.Process(target=self.sort, args=(r_arr, ))

        l_thread.run()
        r_thread.run()
        tmp_arr = self.merge(l_arr, r_arr)
        array[left: left + len(tmp_arr)] = tmp_arr


class ConcurrentMergesortThreadLimited(Mergesort):

    def __init__(self, n_threads=available_cpu_count()):
        super(ConcurrentMergesortThreadLimited, self).__init__()

        self.processes = n_threads
        copy_reg.pickle(types.MethodType, _pickle_method)

    def sort(self, array, left=None, right=None):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        work_arr = array[left:right + 1]
        pool = multiprocessing.Pool(self.processes)

        size = int(math.ceil(float(right + 1 - left) / self.processes))
        work_arr = [work_arr[i * size:(i + 1) * size] for i in range(min(self.processes, right + 1 - left))]
        work_arr = pool.map(self.sort_parallel, work_arr)

        while len(work_arr) > 1:
            extra = work_arr.pop() if len(work_arr) % 2 == 1 else None
            work_arr = [(work_arr[i], work_arr[i + 1]) for i in range(0, len(work_arr), 2)]
            work_arr = pool.map(self.merge, work_arr) + ([extra] if extra else [])

        array[left: left + right + 1] = work_arr[0]

    def sort_parallel(self, array):
        length = len(array)
        if length <= 1:
            return array

        middle = length / 2
        l_arr = self.sort_parallel(array[:middle])
        r_arr = self.sort_parallel(array[middle:])

        return Mergesort.merge(l_arr, r_arr)

    def merge(self, *args):
        if len(args) == 1:
            l_arr, r_arr = args[0]
        else:
            l_arr, r_arr = args

        return super(ConcurrentMergesortThreadLimited, self).merge(l_arr, r_arr)
