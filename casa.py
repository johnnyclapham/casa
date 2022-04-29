# Note: This is the runner for our comparison
# Please see README.md for individual execution
import os
import sys
import argparse
import time
import shutil


def printToolSelection(cryptoguard_flag, flowdroid_flag, quark_flag):
    print(" _____________________________"
          "\n Successful invocation of CASA. Printing configuration."
          "\n --> cryptoguard flag set: {} "
          "\n --> quark flag set:       {} "
          "\n --> flowdroid flag set:   {} "
          "\n _____________________________".format(
        cryptoguard_flag,
        flowdroid_flag,
        quark_flag
    ))


def runToolsOnAPK(cryptoguard_flag, flowdroid_flag, quark_flag, apk_name):
    printToolSelection(cryptoguard_flag, flowdroid_flag, quark_flag)

    if cryptoguard_flag == "1":
        print "cryptoguard is running"
        os.system('java -jar src/cryptoguard/cryptoguard.jar '
                  '-in apk '
                  '-s apks/' + apk_name + ' '
                  '-m SX'
                  '>output/cryptoguard/cryptoguard-terminal-output.txt  2>&1')
    else:
        print "no cryptoguard"

    if flowdroid_flag == "1":
        print "qark is running"
        os.system(
            'python2 src/qark/qark-0.9-alpha.1/qark.py '
            '-p apks/' + apk_name + ' '
            '--source=1 '
            '--exploit=0 '
            '> output/qark/qark-terminal-output.txt')
    else:
        print "no qark"

    if quark_flag == "1":
        print "flowdroid is running"
        os.system(
            'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
            '-a apks/' + apk_name + ' '
            '-p src/flowdroid/android-sdk-macosx/platforms/ '
            '-s src/flowdroid/SourcesAndSinksSusi.txt'
            '>output/flowdroid/flowdroid-terminal-output.txt  2>&1')
    else:
        print "no flowdroid"


def clean_directory(apk_name):
    # tool_name = 'cryptoguard'
    # apk_name = 'test'
    for fileName in os.listdir("./"):
        if fileName.startswith("_C"):
            tool_name = 'cryptoguard'
            # print(fileName)
            new_file_name = tool_name + apk_name+ '.xml'
            new_path = 'output/' + tool_name + '/report/'
            print(new_file_name)
            os.rename(fileName, new_file_name)
            try:
                shutil.move(new_file_name, new_path)
            except:
                print("APK: "+apk_name+" has already been processed. "
                                      "Please delete report in output/ "
                                      "folder if you would like to repeat.")



def main():
    # Note: Get arguments for which tools we wish to execute
    parser = argparse.ArgumentParser()
    parser.add_argument("-cg", "--cryptoguard", help="cryptoguard flag")
    parser.add_argument("-qk", "--quark", help="quark flag")
    parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    args = parser.parse_args()
    # Note: Give the name of the single APK we want to process
    apk_name = 'air.pokemonquiz.apk'
    # Note: Perform analysis on APK using our set T of tools
    runToolsOnAPK(args.cryptoguard, args.flowdroid, args.quark, apk_name)
    # Note: Move report files into output directory
    clean_directory(apk_name)
    # Note: End CASA
    print "CASA has finished. Goodbye!"
    return 0


if __name__ == '__main__':
    main()
