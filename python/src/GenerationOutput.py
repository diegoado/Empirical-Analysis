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

from ioService import Input
from ioService import Output
from datetime import datetime
from sorting.sequential.mergesort import Mergesort
from sorting.sequential.quicksort import RandomQuicksort
from sorting.concurrent.mergesort import ConcurrentMergesort
from sorting.concurrent.quicksort import ConcurrentRandomQuicksort
from sorting.concurrent.mergesort import ConcurrentMergesortThreadLimited
from sorting.concurrent.quicksort import ConcurrentRandomQuicksortThreadLimited


OUT_PATH = './../output/python%s.csv'
INPUT_FOLDER = './../../entrance/input%s.txt'

FILES_NAME = ['SeqMerge', 'SeqQuick', 'ConcMerge', 'ConcQuick', 'ConcMergeLtd', 'ConcQuickLtd']


class GenerationOutput(object):

    def __init__(self):
        super(GenerationOutput, self).__init__()

    def execute(self):
        algorithms = [
            Mergesort(), RandomQuicksort(), ConcurrentMergesort(),
            ConcurrentRandomQuicksort(), ConcurrentMergesortThreadLimited(), ConcurrentRandomQuicksortThreadLimited()
        ]
        out_files = [[] for i in range(6)]

        for i in range(6):
            for j in range(50):
                f_input = Input(INPUT_FOLDER % (j + 1))
                mean = 0.
                for k in range(10):
                    f = f_input.get_file()

                    start = datetime.now()
                    algorithms[i].sort(f)
                    end = datetime.now()

                    mean += (end - start).total_seconds() * 1000

                mean /= 10.
                out_files[i].append(mean)

            out = Output(OUT_PATH % FILES_NAME[i])
            out.save_file(out_files[i])
