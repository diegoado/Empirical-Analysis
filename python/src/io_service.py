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

import os


class Input(object):

    def __init__(self, path):
        super(Input, self).__init__()

        self.path = path

    def get_file(self):
        full_path = os.path.realpath(os.path.expanduser(self.path))

        with open(full_path, 'r') as f:
            arr = [l.rstrip('\n') for l in f.readlines()]

        f.close()
        return arr


class Output(object):

    def __init__(self, path):
        super(Output, self).__init__()

        self.path = path

    def save_file(self, lines):
        full_path = os.path.realpath(os.path.expanduser(self.path))
        path_folder = os.path.dirname(full_path)
        self.makedirs(path_folder)

        with open(full_path, 'w') as f:
            f.writelines(["%s\n" % l for l in lines])

    def makedirs(self, path):
        if not path or os.path.exists(path):
            return

        head, tail = os.path.split(path)
        self.makedirs(head)

        os.makedirs(path)
