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

from threading import Thread

from sorting.concurrent import *
from sorting.sequential.mergesort import Mergesort


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

        l_thread = Thread(target=self.sort, args=(l_arr, ))
        r_thread = Thread(target=self.sort, args=(r_arr, ))

        l_thread.run()
        r_thread.run()
        tmp_arr = self.merge(l_arr, r_arr)
        array[left: left + len(tmp_arr)] = tmp_arr


class ConcurrentMergesortThreadLimited(Mergesort):

    def __init__(self):
        super(ConcurrentMergesortThreadLimited, self).__init__()

    def sort(self, array, left=None, right=None, cores=available_cpu_count()):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        if right - left + 1 < MIN_THREAD_LEN or cores < 2:
            super(ConcurrentMergesortThreadLimited, self).sort(array, left, right)
            return

        mid = (right + 1 - left) / 2

        l_arr = array[left:left + mid]
        r_arr = array[left + mid:right + 1]

        l_thread = Thread(target=self.sort, args=(l_arr, ))
        r_thread = Thread(target=self.sort, args=(r_arr, ))

        l_thread.run()
        r_thread.run()
        tmp_arr = self.merge(l_arr, r_arr)
        array[left:left + len(tmp_arr)] = tmp_arr
