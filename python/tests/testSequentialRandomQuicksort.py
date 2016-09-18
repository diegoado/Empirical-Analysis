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

from unittest import TestCase
from src.sorting.sequential.quicksort import RandomQuicksort


class TestRandomQuicksort(TestCase):

    def setUp(self):
        super(TestRandomQuicksort, self).setUp()

        self.quicksort = RandomQuicksort()

    def tearDown(self):
        super(TestRandomQuicksort, self).tearDown()

    def testSort(self):
        array = [40, 245, -2, 0, 15, 8, 25, -7]
        arr_ordered = [-7, -2, 0, 8, 15, 25, 40, 245]

        self.quicksort.sort(array)
        self.assertEqual(arr_ordered, array)

    def testSortIndex(self):
        array = [40, 245, -2, 0, 15, 8, 25, -7]
        arr_ordered = [40, 245, -2, -7, 0, 8, 15, 25]

        self.quicksort.sort(array, 3, 7)
        self.assertEqual(arr_ordered, array)
