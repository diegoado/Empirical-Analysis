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

from sorting.sorting import Sorting

import random


class RandomQuicksort(Sorting):

    def __init__(self):
        super(RandomQuicksort, self).__init__()

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

        self.sort(array, left, ref - 1)
        self.sort(array, ref + 1, right)

    @staticmethod
    def partition(array, left, right):
        i = left + 1
        j = right
        pivot = array[left]

        while i <= j:
            if array[i] <= pivot:
                i += 1
            elif array[j] > pivot:
                j -= 1
            else:
                array[i], array[j] = array[j], array[i]

        array[left], array[j] = array[j], array[left]
        return j