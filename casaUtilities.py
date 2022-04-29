import os
import sys
import argparse
import time
import shutil
from tqdm import tqdm
# from utilities/casaUtilities import *

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

def parse_reports():
    print("parsing outputs")
