function Get-ProjectRoot {
    return (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
}

function Get-JavaTool {
    param(
        [Parameter(Mandatory = $true)]
        [string] $ToolName
    )

    $root = Get-ProjectRoot
    $localTool = Join-Path $root ".codex-tools\jdk-17\bin\$ToolName.exe"
    if (Test-Path $localTool) {
        return $localTool
    }

    if ($env:JAVA_HOME) {
        $javaHomeTool = Join-Path $env:JAVA_HOME "bin\$ToolName.exe"
        if (Test-Path $javaHomeTool) {
            return $javaHomeTool
        }
    }

    $pathTool = Get-Command $ToolName -ErrorAction SilentlyContinue
    if ($pathTool) {
        return $pathTool.Source
    }

    throw "Could not find $ToolName. Install a JDK or place one under .codex-tools\jdk-17."
}

function Get-JavaSources {
    $root = Get-ProjectRoot
    return Get-ChildItem -Recurse -Path (Join-Path $root 'src') -Filter '*.java' |
        ForEach-Object { $_.FullName }
}
