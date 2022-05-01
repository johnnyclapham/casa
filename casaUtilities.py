import os
import shutil
import xml.etree.ElementTree as ET

def clean_directory(apk_name):
    for fileName in os.listdir("./"):
        if fileName.startswith("_C"):
            tool_name = 'cryptoguard'
            new_file_name = tool_name + "-" + apk_name + '.xml'
            new_path = 'output/' + tool_name + '/reports/'
            os.rename(fileName, new_file_name)
            try:
                shutil.move(new_file_name, new_path)
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


def parse_output_by_apk_and_tool(apk_name, tool_name,casa_output_file):
    print("parsing outputs for apk: "+apk_name)

    if tool_name == 'CryptoGuard':

        target_directory = 'output/cryptoguard/reports'
        target_xml = target_directory + '/' + 'cryptoguard-' + apk_name + '.xml'
        tree = ET.parse(target_xml)
        root = tree.getroot()
        with open(casa_output_file, 'a') as filetowrite:
            filetowrite.write('-> Tool: ' + tool_name + '\n')
            for BugCategory in root.iter('BugCategory'):
                filetowrite.write('    -> '+str(BugCategory.attrib) + '\n')
        print("-> CryptoGuard output parsed")

    if tool_name == 'QARK':
        terminal_location = "output/qark/terminal/qark-terminal-output-"+apk_name+".txt"
        with open(casa_output_file, 'a') as filetowrite:
            filetowrite.write('-> Tool: ' + tool_name + '\n')
            with open(terminal_location) as file:
                for line in file:
                    # Note: Scan log for warnings
                    # TODO: Uncomment if we want to include warnings
                    # if (line.rstrip().startswith("WARNING")):
                    #     finding = line.rstrip()
                    #     character_length_desired = 100
                    #     filetowrite.write('-> ' + finding[0:character_length_desired] + '\n')

                    # Note: Scan log for vulnerabilities
                    if (line.rstrip().startswith("POTENTIAL VULNERABILITY")):
                        finding = line.rstrip()
                        character_length_desired = 100
                        filetowrite.write('    -> ' + finding[0:character_length_desired] + '\n')

        print("-> qark output parsed")

    if tool_name == 'FlowDroid':
        terminal_location = "output/flowdroid/terminal/flowdroid-terminal-output-"+apk_name+".txt"
        with open(casa_output_file, 'a') as filetowrite:
            filetowrite.write('-> Tool: ' + tool_name + '\n')
            with open(terminal_location) as file:
                for line in file:
                    pass
                last_line = line
                # Note: In FlowDroid, the last line of the terminal output
                #       provides the summary of "leaks found"
                if last_line.startswith("[main] INFO soot.jimple.infoflow.android.SetupApplication - Found"):
                    filetowrite.write('    -> ' + last_line + '\n')
                else:
                    error_message = "N/A FlowDroid failed"
                    filetowrite.write('    -> ' + error_message + '\n')
        print("-> FlowDroid output parsed")
