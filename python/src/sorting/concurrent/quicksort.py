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

from multiprocessing import Process, Pipe
from sorting.concurrent import available_cpu_count
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

        parent_conn, child_conn = Pipe()

        p = Process(target=self.sort_parallel, args=(array[left:left + right + 1], child_conn, ))
        p.start()

        array[left:left + right + 1] = parent_conn.recv()
        p.join()

    def sort_parallel(self, array, conn):
        if len(array) <= 1:
            conn.send(array)
            conn.close()
            return

        pivot = array.pop(random.randint(0, len(array) - 1))
        l_arr = [x for x in array if x < pivot]
        r_arr = [x for x in array if x >= pivot]

        parent_conn_left, child_conn_left = Pipe()
        left_proc = Process(target=self.sort_parallel, args=(l_arr, child_conn_left, ))

        parent_conn_right, child_conn_right = Pipe()
        right_proc = Process(target=self.sort_parallel, args=(r_arr, child_conn_right, ))

        left_proc.start()
        right_proc.start()

        conn.send(parent_conn_left.recv() + [pivot] + parent_conn_right.recv())
        conn.close()

        left_proc.join()
        right_proc.join()


class ConcurrentRandomQuicksortThreadLimited(ConcurrentRandomQuicksort):

    def __init__(self, n_threads=available_cpu_count()):
        super(ConcurrentRandomQuicksortThreadLimited, self).__init__()

        self.processes = n_threads

    def sort(self, array, left=None, right=None):
        if not left:
            left = 0
        if not right:
            right = len(array) - 1

        parent_conn, child_conn = Pipe()

        p = Process(target=self.sort_parallel, args=(array[left:left + right + 1], child_conn, self.processes))
        p.start()

        array[left:left + right + 1] = parent_conn.recv()
        p.join()

    def sort_parallel(self, array, conn, processes=None):
        if processes <= 0 or len(array) <= 1:
            conn.send(self.quick(array))
            conn.close()
            return

        pivot = array.pop(random.randint(0, len(array) - 1))
        l_arr = [x for x in array if x < pivot]
        r_arr = [x for x in array if x >= pivot]

        parent_conn_left, child_conn_left = Pipe()
        left_proc = Process(target=self.sort_parallel, args=(l_arr, child_conn_left, processes - 1))

        parent_conn_right, child_conn_right = Pipe()
        right_proc = Process(target=self.sort_parallel, args=(r_arr, child_conn_right, processes - 1))

        left_proc.start()
        right_proc.start()

        conn.send(parent_conn_left.recv() + [pivot] + parent_conn_right.recv())
        conn.close()

        left_proc.join()
        right_proc.join()

    def quick(self, array):
        if len(array) <= 1:
            return array

        pivot = array.pop(random.randint(0, len(array) - 1))
        return self.quick([x for x in array if x < pivot]) + [pivot] + self.quick([x for x in array if x >= pivot])