# Note: This is the runner for our comparison
# Please see README.md for individual execution
import os
import sys
import argparse
import time

# parser = argparse.ArgumentParser()
# os.system('ls')
import time

def printToolSelection(cryptoguard_flag,flowdroid_flag,quark_flag):
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


def runTools(cryptoguard_flag,flowdroid_flag,quark_flag):
    printToolSelection(cryptoguard_flag, flowdroid_flag, quark_flag)

    if (cryptoguard_flag == "1"):
        print "cryptoguard is running"
        os.system('java -jar src/cryptoguard/cryptoguard.jar '
                  '-in apk '
                  '-s apks/air.pokemonquiz.apk '
                  '-m SX'
                  '>output/cryptoguard-terminal-output.txt  2>&1')
    else:
        print "no cryptoguard"

    if (flowdroid_flag == "1"):
        print "qark is running"
        os.system(
            'python2 src/qark/qark-0.9-alpha.1/qark.py '
            '-p apks/air.pokemonquiz.apk '
            '--source=1 '
            '--exploit=0 '
            '> output/qark-terminal-output.txt')
    else:
        print "no qark"

    if (quark_flag == "1"):
        print "flowdroid is running"
        os.system(
            'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
            '-a apks/air.pokemonquiz.apk '
            '-p src/flowdroid/android-sdk-macosx/platforms/ '
            '-s src/flowdroid/SourcesAndSinksSusi.txt'
            '>output/flowdroid-terminal-output.txt  2>&1')
    else:
        print "no flowdroid"

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("-cg", "--cryptoguard", help="cryptoguard flag")
    parser.add_argument("-qk", "--quark", help="quark flag")
    parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    args = parser.parse_args()

    runTools(args.cryptoguard, args.flowdroid, args.quark)

    print "___________________________"
    print "CASA has finished. Goodbye!"
    return 0


if __name__ == '__main__':
    main()
