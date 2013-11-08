# Software License Agreement (BSD License)
#
# Copyright (c) 2009-2011, Eucalyptus Systems, Inc.
# All rights reserved.
#
# Redistribution and use of this software in source and binary forms, with or
# without modification, are permitted provided that the following conditions
# are met:
#
#   Redistributions of source code must retain the above
#   copyright notice, this list of conditions and the
#   following disclaimer.
#
#   Redistributions in binary form must reproduce the above
#   copyright notice, this list of conditions and the
#   following disclaimer in the documentation and/or other
#   materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.
#
# Author: Neil Soman neil@eucalyptus.com
#         Mitch Garnaat mgarnaat@eucalyptus.com

import euca2ools.commands.eucacommand
from boto.roboto.param import Param

class GetConsoleOutput(euca2ools.commands.eucacommand.EucaCommand):

    Description = 'Prints console output from a running instance.'
    Args = [Param(name='instance_id', ptype='string', optional=False,
                  doc="""unique identifier for instance
                  to show the console output for.""")]
    Options = [Param(name='raw', long_name='raw', ptype='boolean',
                     default=False, optional=True,
                     doc='''Display raw output without escaping control
                     characters''')]

    def display_console_output(self, console_output):
        print console_output.instance_id
        print console_output.timestamp
        output = console_output.output or ''
        if not self.raw:
            # Escape control characters
            esc_ords = (list(range(0x00, 0x09)) + list(range(0x0e, 0x1f)) +
                        [0x0b, 0x0c, 0x7f])
            for esc_ord in esc_ords:
                # Small assumption:  we aren't translating ' or "
                output = output.replace(chr(esc_ord),
                                        repr(chr(esc_ord)).strip('\'"'))
        print output

    def main(self):
        conn = self.make_connection_cli()
        return self.make_request_cli(conn, 'get_console_output',
                                   instance_id=self.instance_id)

    def main_cli(self):
        co = self.main()
        self.display_console_output(co)
