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

import re
import multiprocessing

MIN_THREAD_LEN = 1024


def available_cpu_count():
    """ Number of available virtual or physical CPUs on this system. """

    # cpuset may restrict the number of *available* processors
    try:
        cpuset = re.search(r'(?m)^Cpus_allowed:\s*(.*)$', open('/proc/self/status').read())
        if cpuset:
            return bin(int(cpuset.group(1).replace(',', ''), 16)).count('1')
        else:
            return multiprocessing.cpu_count()
    except (IOError, ImportError, NotImplementedError):
        return 1


def _pickle_method(m):
    if m.im_self is None:
        return getattr, (m.im_class, m.im_func.func_name)
    else:
        return getattr, (m.im_self, m.im_func.func_name)
