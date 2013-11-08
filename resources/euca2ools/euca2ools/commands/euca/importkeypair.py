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

class ImportKeyPair(euca2ools.commands.eucacommand.EucaCommand):

    APIVersion = '2010-08-31'
    Description = 'Import a public key created with 3rd party tool'
    Options = [Param(name='file_name',
                     short_name='f', long_name='public-key-file',
                     optional=False, ptype='file',
                     doc='Path to file containing the public key.')]
    Args = [Param(name='key_name', ptype='string',
                  doc='A unique name for the key pair.',
                  optional=False)]
    
    def main(self):
        fp = open(self.file_name)
        key_material = fp.read()
        fp.close()
        conn = self.make_connection_cli()
        return self.make_request_cli(conn, 'import_key_pair',
                                     key_name=self.key_name,
                                     public_key_material=key_material)

    def main_cli(self):
        keypair = self.main()
        if keypair:
            print 'KEYPAIR\t%s\t%s' % (keypair.name, keypair.fingerprint)

