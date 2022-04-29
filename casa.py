# Note: This is the runner for our comparison
# Please see README.md for individual execution
import os
import sys
import argparse
import time

# parser = argparse.ArgumentParser()
# os.system('ls')
import time


def main(cryptoguard_flag, flowdroid_flag, qark_flag):
    # print "Hello World!"

    # ----------------------------------

    if (cryptoguard_flag == "1"):
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        print "cryptoguard is running"
        # java -jar src/cryptoguard/cryptoguard.jar -in apk -s apks/air.pokemonquiz.apk
        os.system('java -jar src/cryptoguard/cryptoguard.jar '
                  '-in apk '
                  '-s apks/air.pokemonquiz.apk')
    else:
        print "no cryptoguard"

    # ----------------------------------

    if (qark_flag == "1"):
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        print "qark is running"
        os.system(
            'python2 src/qark/qark-0.9-alpha.1/qark.py '
            '-p apks/air.pokemonquiz.apk '
            '--source=1 '
            '--exploit=0 ')
        # '-t acceptterms | -b src/flowdroid/android-sdk-macosx')
        # '-t acceptterms | -b android-sdk_r24.0.2-macosx/android-sdk-macosx/platforms')

        # time.sleep(5)
        # print "yam"
        # os.system('n')
        # time.sleep(5)
        # os.system('android-sdk_r24.0.2-macosx/')
    else:
        print "no qark"

    # ----------------------------------

    if (flowdroid_flag == "1"):
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        # print "@@@@@@@@@@@@@@@@@@@@@@"
        print "flowdroid is running"
        os.system(
            'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
            '-a apks/air.pokemonquiz.apk '
            '-p src/flowdroid/android-sdk-macosx/platforms/ '
            '-s src/flowdroid/SourcesAndSinksSusi.txt')
    else:
        print "no flowdroid"

    print "___________________________"
    print "CASA has finished. Goodbye!"
    return 0


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("-cg", "--cryptoguard", help="cryptoguard flag")
    parser.add_argument("-qk", "--quark", help="quark flag")
    parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    args = parser.parse_args()

    print(" _____________________________"
          "\n Successful invocation of CASA. Printing configuration."
          "\n --> cryptoguard flag set: {} "
          "\n --> quark flag set:       {} "
          "\n --> flowdroid flag set:   {} "
          "\n _____________________________".format(
        args.cryptoguard,
        args.flowdroid,
        args.quark
    ))

    main(args.cryptoguard, args.flowdroid, args.quark)
