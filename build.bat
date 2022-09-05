@ECHO OFF

REM Change the path before executing
"D:\soft\BlackBerry JDE 7.1.0\bin\rapc.exe"^
    -quiet^
    -timing^
    -Xlint:-options^
    codename=.\bin\SyncTimeNtp^
    SyncTimeNtp.rapc^
    import="..\..\..\soft\BlackBerry JDE 7.1.0\lib\net_rim_api.jar"^
    .\resource\Localization.rrc^
    .\resource\Localization_en.rrc^
    .\src\com\boris1993\timesyncntp\utils\HexUtils.java^
    .\src\com\boris1993\timesyncntp\utils\NtpPacketUtils.java^
    .\src\com\boris1993\timesyncntp\WideButton.java^
    .\src\com\boris1993\timesyncntp\LocalizationResource.java^
    .\src\com\boris1993\timesyncntp\NtpConstants.java^
    .\src\com\boris1993\timesyncntp\SyncTimeNtp.java^
    .\src\com\boris1993\timesyncntp\MainAppScreen.java^
