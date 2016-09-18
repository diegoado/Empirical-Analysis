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
from sorting.sequential.quicksort import RandomQuicksort

import random


class ConcurrentRandomQuicksort(RandomQuicksort):

    def __init__(self):
        super(ConcurrentRandomQuicksort, self).__init__()

    def sort(self, array, left=None, right=None):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        if left >= right:
            return

        pivot = left + random.randrange(right + 1 - left)
        array[right], array[pivot] = array[pivot], array[right]

        ref = self.partition(array, left, right)

        l_thread = Thread(target=self.sort, args=(array, left, ref - 1, ))
        r_thread = Thread(target=self.sort, args=(array, ref + 1, right, ))

        l_thread.run()
        r_thread.run()


class ConcurrentRandomQuicksortThreadLimited(RandomQuicksort):

    def __init__(self):
        super(ConcurrentRandomQuicksortThreadLimited, self).__init__()

    def sort(self, array, left=None, right=None, cores=available_cpu_count()):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        if right + 1 - left < MIN_THREAD_LEN or cores < 2:
            super(ConcurrentRandomQuicksortThreadLimited, self).sort(array, left, right)
            return

        pivot = left + random.randrange(right + 1 - left)
        array[right], array[pivot] = array[pivot], array[right]

        ref = self.partition(array, left, right)

        l_thread = Thread(target=self.sort, args=(array, left, ref - 1, cores / 2))
        r_thread = Thread(target=self.sort, args=(array, ref + 1, right, cores - cores / 2))

        l_thread.run()
        r_thread.run()
