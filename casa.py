# Note: This is the runner for our comparison
# Please see README.md for individual execution
import os

from casaUtilities import *
import argparse
from tqdm.auto import tqdm
import multiprocessing
import time
from os.path import exists
from multiprocessing import Process



def print_tool_selection(cryptoguard_flag, qark_flag, flowdroid_flag, apk_name):
    print(" _____________________________"
          "\n Successful invocation of CASA. Printing configuration."
          "\n --> cryptoguard flag set: {} "
          "\n --> qark flag set:       {} "
          "\n --> flowdroid flag set:   {} "
          "\n --> APK to scan:   {} "
          "\n _____________________________".format(
        cryptoguard_flag,
        qark_flag,
        flowdroid_flag,
        apk_name
    ))


def run_cryptoguard(apk_name):
    os.system('java -jar src/cryptoguard/cryptoguard.jar '
              '-in apk '
              '-s apks/' + apk_name + ' -m SX ' +
              '>output/cryptoguard/terminal/cryptoguard-terminal-output-'
              + apk_name + '.txt  2>&1')


def run_qark(apk_name):
    path_to_file = 'output/qark/terminal/qark-terminal-output-' + apk_name + '.txt'
    path_to_report_file = 'output/qark/reports/report-' + apk_name
    if exists(path_to_report_file):
        print("skipping apk: "+apk_name)
    else:
        os.system(
            'python2 src/qark/qark-0.9-alpha.1/qark.py '
            '-p apks/' + apk_name + ' ' +
            '--source=1 ' + '--exploit=0 ' + ' -report-type=json'
            + '&> output/qark/terminal/qark-terminal-output-' + apk_name + '.txt')

    # os.system(
    #     'python2 src/qark/qark-0.9-alpha.1/qark.py '
    #     '-p apks/' + apk_name + ' ' +
    #     '--source=1 ' + '--exploit=0 ' + ' -report-type=json')


def run_flowdroid(apk_name):
    os.system(
        'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
        '-a apks/' + apk_name + ' ' +
        '-p src/flowdroid/android-sdk-macosx/platforms/ ' +
        '-s src/flowdroid/SourcesAndSinksSusi.txt' +
        '> output/flowdroid/terminal/flowdroid-terminal-output-' +
        apk_name + '.txt  2>&1')


def run_tools_on_apk(cryptoguard_flag, qark_flag, flowdroid_flag, apk_name):
    # Note: Print which tools the user has selected to execute
    # print_tool_selection(cryptoguard_flag, qark_flag, flowdroid_flag, apk_name)

    # Note: If the flag is active, execute the tool
    if flowdroid_flag == "1":
        print "flowdroid is running"
        try:
            # p = multiprocessing.Process(target=run_flowdroid, name="run_flowdroid",
            #                             args=(apk_name,))
            # p.start()
            # # Note: Wait for number_seconds
            # timeout_seconds = 30
            # time.sleep(timeout_seconds)
            # p.terminate()
            # p.join()

            # run_flowdroid(apk_name)

            p = Process(target=run_flowdroid, args=(apk_name,))
            # timeout = 180
            timeout = 720
            p.start()
            counter = 0
            while p.is_alive():
                time.sleep(1)
                counter += 1
                print(str(counter) + ' seconds have passed')
                if counter > timeout :
                    print "Child process consumed too much run-time. Going to kill it!"
                    os.kill(p.pid)
                    break

            # os.system(
            #     'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
            #     '-a apks/' + apk_name + ' ' +
            #     '-p src/flowdroid/android-sdk-macosx/platforms/ ' +
            #     '-s src/flowdroid/SourcesAndSinksSusi.txt' +
            #     '> output/flowdroid/terminal/flowdroid-terminal-output-' +
            #     apk_name + '.txt  2>&1')
        except:
            print('flowdroid failed to run')
    else:
        print "flowdroid is not running"

    if cryptoguard_flag == "1":
        print "cryptoguard is running"
        try:
            # p = multiprocessing.Process(target=run_cryptoguard, name="run_cryptoguard",
            #                             args=(apk_name,))
            # p.start()
            # # Note: Wait for number_seconds
            # timeout_seconds = 240
            # time.sleep(timeout_seconds)
            # p.terminate()
            # p.join()

            # run_cryptoguard(apk_name)

            p = Process(target=run_cryptoguard, args=(apk_name,))
            # timeout = 180
            timeout = 720
            p.start()
            counter = 0
            while p.is_alive():
                time.sleep(1)
                counter += 1
                print(str(counter) + ' seconds have passed')
                if counter > timeout :
                    print "Child process consumed too much run-time. Going to kill it!"
                    os.kill(p.pid)
                    break

            # os.system('java -jar src/cryptoguard/cryptoguard.jar '
            #           '-in apk '
            #           '-s apks/' + apk_name + ' -m SX ' +
            #           '>output/cryptoguard/terminal/cryptoguard-terminal-output-'
            #           + apk_name + '.txt  2>&1')
        except:
            print('cryptoguard failed to run')
        # Note: Move report files into output directory
        # print "INFO: cleaning cryptoguard output"
        clean_directory(apk_name)
    else:
        print "cryptoguard is not running"

    if qark_flag == "1":
        print "qark is running"
        path_to_file = 'output/qark/terminal/qark-terminal-output-' + apk_name + '.txt'
        if exists(path_to_file):

            print("skipping apk: "+apk_name)
        else:

            try:
                p = multiprocessing.Process(target=run_qark, name="run_qark",
                                            args=(apk_name,))
                # p.start()
                # # Note: Wait for number_seconds
                # timeout_seconds = 180
                # time.sleep(timeout_seconds)
                # p.terminate()
                # p.join()

                p = Process(target=run_qark, args=(apk_name,))
                # timeout = 180
                timeout = 720
                p.start()
                counter = 0
                while p.is_alive():
                    time.sleep(1)
                    counter += 1
                    print(str(counter) + ' seconds have passed')
                    if counter > timeout :
                        print "Child process consumed too much run-time. Going to kill it!"
                        os.kill(p.pid)
                        break



                # run_qark(apk_name)
                # timeout_seconds = 60
                # time.sleep(timeout_seconds)
                # os.system('c')

                # os.system(
                #     'python2 src/qark/qark-0.9-alpha.1/qark.py '
                #     '-p apks/' + apk_name + ' ' +
                #     '--source=1 ' + '--exploit=0 ' + ' -report-type=json'
                #     + '&> output/qark/terminal/qark-terminal-output-' + apk_name + '.txt')
                # os.system('y')
            except:
                print('qark failed to run')
            move_qark_report(apk_name)
    else:
        print "qark is not running"

    #

    # if flowdroid_flag == "1":
    #     print "flowdroid is running"
    #     try:
    #         os.system(
    #             'java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar '
    #             '-a apks/' + apk_name + ' ' +
    #             '-p src/flowdroid/android-sdk-macosx/platforms/ ' +
    #             '-s src/flowdroid/SourcesAndSinksSusi.txt' +
    #             '> output/flowdroid/terminal/flowdroid-terminal-output-' +
    #             apk_name + '.txt  2>&1')
    #     except:
    #         print('flowdroid failed to run')
    # else:
    #     print "flowdroid is not running"


def execute_tools(args):
    # Note: For each application in Set A, scan using tools in Set T
    # Note: Using tqdm for progress bar -> github.com/tqdm/tqdm
    for file_name in tqdm(os.listdir("./apks/"), position=0, leave=True):
        if file_name.endswith('apk'):
            # Note: We scan each APK
            apk_name = file_name
            print "\n --> APK to scan:   {}  \r".format(apk_name)
            # Note: Perform analysis on APK using our set T of tools
            # TODO: Uncomment below when we want to re-enable tool execution!
            run_tools_on_apk(args.cryptoguard, args.qark, args.flowdroid, apk_name)

            # p = multiprocessing.Process(target=run_tools_on_apk, name="run_tools_on_apk",
            #                             args=(args.cryptoguard, args.qark, args.flowdroid, apk_name,))
            # p.start()
            # # Note: Wait for number_seconds
            # timeout_seconds = 240
            # time.sleep(timeout_seconds)
            # p.terminate()
            # p.join()


def parse_reports(casa_output_file):
    success_count = 0
    fail_count = 0

    # Note: erase casa_output_file for a fresh output log
    with open(casa_output_file, 'w') as filetowrite:
        filetowrite.write('CASA has extracted outputs.\n\n')

    # Note: For each apk in 'apks/'
    for file_name in tqdm(os.listdir("./apks/")):
        if file_name.endswith('apk'):
            # Note: We parse the output of each apk on each specified tool
            apk_name = file_name
            with open(casa_output_file, 'a') as filetowrite:
                filetowrite.write('\n\n\n\n\n\n')
                filetowrite.write('\n========================')
                filetowrite.write('\n========================')
                filetowrite.write('\n========================\n')
                filetowrite.write('Input APK: ' + apk_name + '\n')
                filetowrite.write('------------------------\n')
            try:
                # # Note: We parse the output of each apk on each specified tool
                # apk_name = file_name
                # with open(casa_output_file, 'a') as filetowrite:
                #     filetowrite.write('\n\n\n\n\n\n')
                #     filetowrite.write('\n========================')
                #     filetowrite.write('\n========================')
                #     filetowrite.write('\n========================\n')
                #     filetowrite.write('Input APK: ' + apk_name + '\n')
                #     filetowrite.write('------------------------\n')

                # # Note: We parse the output of each apk on each specified tool
                # apk_name = file_name

                tool_name = "CryptoGuard"
                parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
                with open(casa_output_file, 'a') as filetowrite:
                    filetowrite.write('------------------------\n')

                tool_name = "QARK"
                parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
                with open(casa_output_file, 'a') as filetowrite:
                    filetowrite.write('------------------------\n')

                tool_name = "FlowDroid"
                parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
                with open(casa_output_file, 'a') as filetowrite:
                    filetowrite.write('------------------------\n')

                success_count += 1
            except:
                print("*********failed to parse APK: " + apk_name)
                parse_failed(casa_output_file)
                fail_count += 1
    print("APK CASA reports successfully generated:  " + str(success_count))
    print("APK CASA reports with errors:  " + str(fail_count))


def main():
    # Note: Get arguments for which tools we wish to execute
    parser = argparse.ArgumentParser()
    parser.add_argument("-cg", "--cryptoguard", help="cryptoguard flag")
    parser.add_argument("-qk", "--qark", help="qark flag")
    parser.add_argument("-fd", "--flowdroid", help="flowdroid flag")
    args = parser.parse_args()

    # print_tool_selection()
    print_tool_selection(args.cryptoguard, args.qark, args.flowdroid, " ")

    # Note: Execute our runner script to automatically evaluate our APK
    #       using all tools
    execute_tools(args)  # TODO: Comment/ Uncomment as desird

    # Note: Specify which output text file to write casa report to
    casa_output_file = 'casa_output.txt'
    parse_reports(casa_output_file)

    # # Note: For each application in Set A, scan using tools in Set T
    # # Note: Using tqdm for progress bar -> github.com/tqdm/tqdm
    # for file_name in tqdm(os.listdir("./apks/"), position=0, leave=True):
    #     if file_name.endswith('apk'):
    #         # Note: We scan each APK
    #         apk_name = file_name
    #         print("\n --> APK to scan:   {}  \r").format(apk_name)
    #         # Note: Perform analysis on APK using our set T of tools
    #         # TODO: Uncomment below when we want to re-enable tool execution!
    #         # run_tools_on_apk(args.cryptoguard, args.qark, args.flowdroid, apk_name)
    #
    #         p = multiprocessing.Process(target=run_tools_on_apk, name="run_tools_on_apk", args=(args.cryptoguard, args.qark, args.flowdroid, apk_name,))
    #         p.start()
    #         # Note: Wait for 4 minutes
    #         time.sleep(240)
    #         p.terminate()
    #         p.join()
    # Note: Wait for 4 minutes
    # time.sleep(240)
    # p.terminate()
    # p.join()

    # # Note: After tools have run, directories are cleaned for next execution
    #
    # # TODO: Parse the reports from tool output
    # casa_output_file = 'casa_output.txt'
    #
    # # Note: erase casa_output_file for a fresh output log
    # with open(casa_output_file, 'w') as filetowrite:
    #     filetowrite.write('CASA has extracted outputs.\n\n')
    #
    # # Note: For each apk in 'apks/'
    # for file_name in tqdm(os.listdir("./apks/")):
    #     if file_name.endswith('apk'):
    #         with open(casa_output_file, 'a') as filetowrite:
    #             filetowrite.write('\n\n\n\n\n\n')
    #             filetowrite.write('\n========================')
    #             filetowrite.write('\n========================')
    #             filetowrite.write('\n========================\n')
    #             filetowrite.write('Input APK: ' + apk_name + '\n')
    #             filetowrite.write('------------------------\n')
    #
    #         # Note: We parse the output of each apk on each specified tool
    #         apk_name = file_name
    #         tool_name = "CryptoGuard"
    #         parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
    #         with open(casa_output_file, 'a') as filetowrite:
    #             filetowrite.write('------------------------\n')
    #         tool_name = "QARK"
    #         parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
    #         with open(casa_output_file, 'a') as filetowrite:
    #             filetowrite.write('------------------------\n')
    #         tool_name = "FlowDroid"
    #         parse_output_by_apk_and_tool(apk_name, tool_name, casa_output_file)
    #
    #         with open(casa_output_file, 'a') as filetowrite:
    #             filetowrite.write('------------------------\n')
    # Note: End CASA
    print "CASA has finished. Goodbye!"
    return 0


if __name__ == '__main__':
    main()

# def clean_directory(apk_name):
#     for fileName in os.listdir("./"):
#         if fileName.startswith("_C"):
#             tool_name = 'cryptoguard'
#             new_file_name = tool_name + "-" + apk_name + '.xml'
#             new_path = 'output/' + tool_name + '/reports/'
#             print(new_file_name)
#             os.rename(fileName, new_file_name)
#             try:
#                 shutil.move(new_file_name, new_path)
#             except:
#                 print("-> WARNING: APK: " + apk_name + " already has a CryptoGuard report. " +
#                       "\n-> INFO: Please delete report in output/qark/reports/report-" +
#                       apk_name +
#                       " folder if you would like to repeat.")
#                 os.remove(new_file_name)
#
#
# def move_qark_report(apk_name):
#     source_dir = 'src/qark/qark-0.9-alpha.1/report'
#     target_dir = 'output/qark/reports/report-' + apk_name
#     try:
#         file_names = os.listdir(source_dir)
#         for file_name in file_names:
#             shutil.move(os.path.join(source_dir, file_name), target_dir)
#         os.rmdir(source_dir)
#     except:
#         print("-> WARNING: APK: " + apk_name + " already has a QARK report. " +
#               "\n-> INFO: Please delete report in output/qark/reports/report-" +
#               apk_name +
#               " folder if you would like to repeat.")
#         shutil.rmtree(source_dir)
