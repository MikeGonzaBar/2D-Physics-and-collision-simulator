param(
    [string] $OutputDir = ''
)

$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\java-env.ps1"

$root = Get-ProjectRoot
$javadoc = Get-JavaTool 'javadoc'
if (-not $OutputDir) {
    $OutputDir = Join-Path $root 'build\docs'
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
$sources = @(Get-JavaSources)

& $javadoc -quiet -Xdoclint:none -d $OutputDir $sources
if ($LASTEXITCODE -ne 0) {
    throw "javadoc failed with exit code $LASTEXITCODE"
}

Write-Host "Generated Javadocs in $OutputDir"
