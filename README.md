# everquest-effort-analyzer
EverQuest player effort (casting, damage dealt, damage received, etc.) analyzer

# Usage

Setup:

```
$ git clone https://github.com/edmazur/everquest-effort-analyzer
$ cd everquest-effort-analyzer
```

Run:

```
$ git pull
$ ./gradlew installDist
$ ./build/install/everquest-player-dumper/bin/everquest-effort-analyzer \
      $EQ_INSTALL_DIRECTORY \
      $TIMEZONE \
      $SERVER \
      $CHARACTER \
      $START_TIMESTAMP \
      $END_TIMESTAMP \
      $REPORT_NAME
```

Parameter notes:

* $EQ_INSTALL_DIRECTORY - The directory where EverQuest is installed, e.g. `/opt/everquest/EverQuest\ Project\ 1999`.
* $TIMEZONE - The timezone of the log file, e.g. `America/New_York`.
* $SERVER - The server of the character, e.g. `green`.
* $CHARACTER - The name of the character, e.g. `Stanvern`.
* $START_TIMESTAMP - EQ log format. Starting point.
* $END_TIMESTAMP - EQ log format. Ending point.
* $REPORT_NAME - Label shown at top of report.

Gotchas:

* The timestamp of the last line in the log file must be after $END_TIMESTAMP or
  the program will hang. So if you are parsing a standalone file, set
  $END_TIMESTAMP to 1 second before the timestamp in the last line.

* Run `everquest-player-dumper` first over as many logs as possible
  (especially the one being used as input for the analysis).
