# Membership Due Calculator

A JAR application built using Java and JavaFX that calculates the membership dues assessed for the members of an electric cooperative. 
Dues are assessed in equal proportions on three components: **equal payment**, the **number of electric meters served**, and **revenue minus cost of power**.
 <br /><br /><br />
The application gives users the option to choose between three main functions. 
<img src= /images/LaunchScene.png width="600">
<br /><br />
Users can enter updated information used to calculate dues for a specific member.
<img src= /images/EnterMemberInfo.png width="600">
<br /><br />
Users can also view full reports of dues assessed per year and dues assessed for a specific member.
<img src= /images/FullReport.png width="600">
<img src= /images/MemberDuesAssessed.png width="600">

**Note:** The membership information displayed in the images has been modified to protect the privacy of members. <br /><br />

For more detailed information behind the calculations of each due component, please refer to `Documentation/Crit_C_Development.pdf`.
<br /><br />
The Eclipse source code of the project can be found in `DueCalculator/src/`. The `DueCalculator.jar` is a runnable JAR file of the application. It scans in 
`duesdata.txt`, a data file of membership information used to calculate dues. However, this data file has not been provided due to privacy concerns. 
