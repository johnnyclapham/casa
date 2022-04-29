# Note: This is the runner for our comparison
# Please see README.md for individual execution
import os
import sys
import argparse
import time
import shutil


def printToolSelection(cryptoguard_flag, quark_flag, flowdroid_flag,apk_name):
    print(" _____________________________"
          "\n Successful invocation of CASA. Printing configuration."
          "\n --> cryptoguard flag set: {} "
          "\n --> quark flag set:       {} "
          "\n --> flowdroid flag set:   {} "
          "\n --> APK to scan:   {} "
          "\n _____________________________".format(
        cryptoguard_flag,
        quark_flag,
        flowdroid_flag,
        apk_name
        # quark_flag
    ))


def runToolsOnAPK(cryptoguard_flag, quark_flag, flowdroid_flag, apk_name):
    # Note: Print which tools the user has selected to execute
    printToolSelection(cryptoguard_flag, quark_flag, flowdroid_flag,apk_name)

    # Note: If the flag is active, execute the tool
    if cryptoguard_flag == "1":
        print "cryptoguard is running"
        try:
            os.system('java -jar src/cryptoguard/cryptoguard.jar '
                      '-in apk '
                      '-s apks/' + apk_name + ' -m SX ' +
                      '>output/cryptoguard/cryptoguard-terminal-output-'
                      + apk_name + '.txt  2>&1')
        except:
            print('cryptoguard failed to run')
        # Note: Move report files into output directory
        clean_directory(apk_name)
    else:
        print "cryptoguard is not running"

    if quark_flag == "1":
        print "qark is running"
        try:
            os.system(
                'python2 src/qark/qark-0.9-alpha.1/qark.py '
                '-p apks/' + apk_name + ' ' +
                '--source=1 ' + '--exploit=0 '
                + '&> output/qark/qark-terminal-output-' + apk_name + '.txt')
        except:
            print('qark failed to run')
        move_qark_report(apk_name)
    else:
        print "qark is not running"

    if flowdroid_flag == "1":
        print "flowdroid is running"
        try:
            os.system(
                'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
                '-a apks/' + apk_name + ' ' +
                '-p src/flowdroid/android-sdk-macosx/platforms/ ' +
                '-s src/flowdroid/SourcesAndSinksSusi.txt' +
                '> output/flowdroid/flowdroid-terminal-output-' +
                apk_name + '.txt  2>&1')
        except:
            print('flowdroid failed to run')
    else:
        print "flowdroid is not running"


def clean_directory(apk_name):
    # tool_name = 'cryptoguard'
    # apk_name = 'test'
    for fileName in os.listdir("./"):
        if fileName.startswith("_C"):
            tool_name = 'cryptoguard'
            # print(fileName)
            new_file_name = tool_name + "-" + apk_name + '.xml'
            new_path = 'output/' + tool_name + '/reports/'
            print(new_file_name)
            os.rename(fileName, new_file_name)
            try:
                shutil.move(new_file_name, new_path)
                # os.remove(new_file_name)
            except:
                print("-> WARNING: APK: " + apk_name + " already has a CryptoGuard report. " +
                      "\n-> INFO: Please delete report in output/qark/reports/report-" +
                      apk_name +
                      " folder if you would like to repeat.")
                os.remove(new_file_name)


def move_qark_report(apk_name):
    source_dir = 'src/qark/qark-0.9-alpha.1/report'
    target_dir = 'output/qark/reports/report-' + apk_name
    try:
        # source_dir = 'src/qark/qark-0.9-alpha.1/report'
        # target_dir = 'output/qark/reports/report-' + apk_name
        file_names = os.listdir(source_dir)
        for file_name in file_names:
            shutil.move(os.path.join(source_dir, file_name), target_dir)
        os.rmdir(source_dir)
    except:
        print("-> WARNING: APK: " + apk_name + " already has a QARK report. " +
              "\n-> INFO: Please delete report in output/qark/reports/report-" +
              apk_name +
              " folder if you would like to repeat.")
        shutil.rmtree(source_dir)
        # os.rmdir(source_dir)
    # Note: Delete qark report folder so it is ready for next execution
    # os.rmdir(source_dir)

    # new_file_name = 'qark' + apk_name + '.xml'
    # shutil.move(new_file_name, new_path)


def main():
    # Note: Get arguments for which tools we wish to execute
    parser = argparse.ArgumentParser()
    parser.add_argument("-cg", "--cryptoguard", help="cryptoguard flag")
    parser.add_argument("-qk", "--quark", help="quark flag")
    parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    # parser.add_argument("-qk", "--quark", help="quark flag")
    # parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    args = parser.parse_args()
    # Note: Give the name of the single APK we want to process
    apk_name = 'air.pokemonquiz.apk'
    # Note: Perform analysis on APK using our set T of tools
    runToolsOnAPK(args.cryptoguard, args.quark, args.flowdroid, apk_name)
    # # Note: Move report files into output directory
    # clean_directory(apk_name)
    # Note: End CASA
    print "CASA has finished. Goodbye!"
    return 0


if __name__ == '__main__':
    main()
