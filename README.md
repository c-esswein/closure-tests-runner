# ClosureTests Runner

Simple java-Tool to execute js-unit tests from the ClosureLibrary. Selenium is used to automate browsers and the result will be written into a standard xml file which can be used in Jenkins.

Parameter

    -testsfile: Url to alltests.js which contains _allTests variable with array of testcases
    -testserver: Basepath as prefix for urls
    -outputfile: Outputpath for xml-report