import os
import shutil
import xml.etree.ElementTree as ET


def clean_directory(apk_name):
    for fileName in os.listdir("./"):
        if fileName.startswith("_C"):
            tool_name = 'cryptoguard'
            new_file_name = tool_name + "-" + apk_name + '.xml'
            new_path = 'output/' + tool_name + '/reports/'
            # print(new_file_name)
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

def parse_output_by_apk_and_tool(apk_name,tool_name):
    print("parsing outputs")

    if tool_name== 'CryptoGuard':
        target_directory = 'output/cryptoguard/reports'
        folder_to_parse = os.listdir(target_directory)
        for file_name in folder_to_parse:
            print(file_name)
            tree = ET.parse(target_directory+'/'+file_name)
            root = tree.getroot()
            casa_output_file ='casa_output.txt'
            with open(casa_output_file, 'w') as filetowrite:
                filetowrite.write('APK: '+apk_name+'\n\n')
                filetowrite.write('Tool: '+tool_name+'\n')
                for BugCategory in root.iter('BugCategory'):
                    filetowrite.write(str(BugCategory.attrib)+'\n')

