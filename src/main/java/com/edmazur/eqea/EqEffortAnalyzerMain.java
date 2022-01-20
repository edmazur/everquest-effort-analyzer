package com.edmazur.eqea;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;

import com.edmazur.eqlp.EqLog;

public class EqEffortAnalyzerMain {

  public static void main(String[] args) {
    // TODO: Validate input.
    final Path eqInstallDirectory = Paths.get(args[0]);
    final ZoneId timezone = ZoneId.of(args[1]);
    final String server = args[2];
    final String character = args[3];

    EqLog eqLog = new EqLog(
        eqInstallDirectory,
        timezone,
        server,
        character,

        // 1/18 HoT
        // [Tue Jan 18 19:04:03 2022] You have entered Temple of Veeshan.
        // $ date -d "Tue Jan 18 19:04:03 2022" +%s
        // 1642550643
        Instant.ofEpochSecond(1642550643),
        // [Tue Jan 18 23:08:28 2022] You have entered Western Wastes.
        // $ date -d "Tue Jan 18 23:08:28 2022" +%s
        // 1642565308
        Instant.ofEpochSecond(1642565308));

//        // 1/6 Sky
//        // [Thu Jan 06 19:25:13 2022] You have entered Plane of Air.
//        // $ date -d "Thu Jan 06 19:25:13 2022" +%s
//        // 1641515113
//        Instant.ofEpochSecond(1641515113),
//        // [Thu Jan 06 23:32:53 2022] You have entered East Freeport.
//        // $ date -d "Thu Jan 06 23:32:53 2022" +%s
//        // 1641529973
//        Instant.ofEpochSecond(1641529973));

    EqEffortListener eqEffortListener = new EqEffortListener();
    eqLog.addListener(eqEffortListener);
    eqLog.run();
    EqEffortPrinter.print(eqEffortListener.getEfforts());
  }

}