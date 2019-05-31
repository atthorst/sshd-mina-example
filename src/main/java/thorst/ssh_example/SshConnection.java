/**
 * Copyright 2019 Drew Thorstensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package thorst.ssh_example;

public class SshConnection {

  private String username;
  private String password;
  private String hostname;

  public SshConnection(String username, String password, String hostname) {
    super();
    this.username = username;
    this.password = password;
    this.hostname = hostname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getHostname() {
    return hostname;
  }

}
