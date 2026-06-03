param(
    [string] $ClassesDir = ''
)

$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\java-env.ps1"

$root = Get-ProjectRoot
$java = Get-JavaTool 'java'
if (-not $ClassesDir) {
    $ClassesDir = Join-Path $root 'build\classes'
}

& "$PSScriptRoot\build.ps1" -OutputDir $ClassesDir
& $java -cp $ClassesDir ProyectoPOO.Main
