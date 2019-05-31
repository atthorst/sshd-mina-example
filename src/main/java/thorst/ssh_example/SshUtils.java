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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

/**
 * Example utility to use SSH via Apache Mina SSHD
 * 
 * @author Drew Thorstensen
 *
 */
public final class SshUtils {

  private SshUtils() {}

  /**
   * Runs a SSH command against a remote system.
   * 
   * @param conn Defines the connection to the system.
   * @param cmd The command to run. Should generally be fully qualified (ex. instead of 'ls -la',
   *        use '/bin/ls -la')
   * @param timeout The amount of time to wait for the command to run before timing out. This is in
   *        seconds. This is used as two separate timeouts, one for login another for command
   *        execution.
   * @return The {@link SshResponse} contains the output of a successful command.
   * @throws SshTimeoutException Raised if the command times out.
   * @throws IOException Raised in the event of a general failure (wrong authentication or something
   *         of that nature).
   */
  public static SshResponse runCommand(SshConnection conn, String cmd, long timeout)
      throws SshTimeoutException, IOException {
    SshClient client = SshClient.setUpDefaultClient();

    try {
      // Open the client
      client.start();

      // Connect to the server
      ConnectFuture cf = client.connect(conn.getUsername(), conn.getHostname(), 22);
      ClientSession session = cf.verify().getSession();
      session.addPasswordIdentity(conn.getPassword());
      session.auth().verify(TimeUnit.SECONDS.toMillis(timeout));

      // Create the exec and channel its output/error streams
      ChannelExec ce = session.createExecChannel(cmd);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ByteArrayOutputStream err = new ByteArrayOutputStream();
      ce.setOut(out);
      ce.setErr(err);

      // Execute and wait
      ce.open();
      Set<ClientChannelEvent> events =
          ce.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(timeout));
      session.close(false);

      // Check if timed out
      if (events.contains(ClientChannelEvent.TIMEOUT)) {
        throw new SshTimeoutException(cmd, conn.getHostname(), timeout);
      }

      // Respond
      return new SshResponse(out.toString(), err.toString(), ce.getExitStatus());
    } finally {
      client.stop();
    }

  }
}
