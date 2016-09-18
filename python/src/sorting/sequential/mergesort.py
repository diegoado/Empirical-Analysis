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

from src.sorting.sorting import Sorting


class Mergesort(Sorting):

    def __init__(self):
        super(Mergesort).__init__()

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

        self.sort(l_arr)
        self.sort(r_arr)

        tmp_arr = self.merge(l_arr, r_arr)
        array[left:len(tmp_arr)] = tmp_arr

    @staticmethod
    def merge(l_arr, r_arr):
        k = 0
        i = j = 0

        tmp_arr = []
        while len(l_arr) > i and len(r_arr) > j:
            if l_arr[i] < r_arr[j]:
                tmp_arr[k] = l_arr[i]
                i += 1
            else:
                tmp_arr[k] = r_arr[j]
                j += 1
            k += 1
        while i < len(l_arr):
            tmp_arr[k] = l_arr[i]
            i += 1
            k += 1
        while j < len(r_arr):
            tmp_arr[k] = r_arr[j]
            j += 1
            k += 1

        return tmp_arr
