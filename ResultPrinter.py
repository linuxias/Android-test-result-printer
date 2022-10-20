import sys
import xml.etree.ElementTree as ET
import argparse
import os
import re

from rich.text import Text
from rich.console import Console
from rich.table import Table
from rich.panel import Panel

console = Console()
summary_table = Table(
            show_header=True, header_style="bold magenta",
            title="Android Test Summary", title_style="bold",
            expand=True)

success_table = Table(
            show_header=True, header_style="bold magenta",
            title="Android Test Success Result", title_style="bold",
            expand=True)

failure_table = Table(
            show_header=True, header_style="bold magenta",
            title="Android Test Failure Result", title_style="bold",
            expand=True)

class Result:
    def __init__(
        self, name, classname, time, result, failure=None
    ) -> None:
        self.name = name
        self.classname = classname
        self.time = time
        self.result = result
        self.failure = failure

def find_test_result_xml():
    result_path = []
    for dpath, dnames, fnames in os.walk("."):
        for i, fname in enumerate([os.path.join(dpath, fname) for fname in fnames]):
            if re.search('TEST-.*.xml', fname):
                result_path.append(fname)

    return result_path

def init_printer_format():
    summary_table.add_column("Total test")
    summary_table.add_column("Success")
    summary_table.add_column("Failure")
    summary_table.add_column("Total time")

    success_table.add_column("Name", style="dim")
    success_table.add_column("Class Name")
    success_table.add_column("Time", justify="right")

    failure_table.add_column("TestName", style="dim", no_wrap=True)
    failure_table.add_column("Failure reason", no_wrap=True)

def parse_unit_test():
    pass

def parse_inst_test(xml_file):
    tree = ET.parse(xml_file)
    root = tree.getroot()

    success_result = []
    failure_result = []

    for testcase in root.findall("testcase"):
        attr = testcase.attrib
        name = attr.get("name")
        classname = attr.get("classname")
        time = attr.get("time")

        failure = testcase.find("failure")
        if (failure == None):
            success_result.append(Result(name, classname, time, "PASS"))
        else:
            failure_result.append(Result(name, classname, time, "FAIL", failure.text))

    return success_result, failure_result

def main():
    print ("")
    result_path = find_test_result_xml()
    if ( len(result_path) == 0 ):
        print("Not found test result files. Please check what tests were executed successfully.")
        exit(-1)

    init_printer_format()

    for path in result_path:
        s, f = parse_inst_test(path)
        time = 0
        for _ in s:
            success_table.add_row(_.name, _.classname, _.time)
            time = float(_.time)
        for _ in f:
            failure_table.add_row(f"{_.name}\n({_.classname})", _.failure)
            time += float(_.time)
        summary_table.add_row(str(len(s) + len(f)), str(len(s)), str(len(f)), str(time))

    console.print(summary_table)
    console.print("")
    console.print(success_table)
    console.print("")
    console.print(failure_table)

if __name__ == "__main__":
    main()
