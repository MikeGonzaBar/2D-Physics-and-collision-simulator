param(
    [string] $OutputDir = ''
)

$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\java-env.ps1"

$root = Get-ProjectRoot
$javac = Get-JavaTool 'javac'
if (-not $OutputDir) {
    $OutputDir = Join-Path $root 'build\classes'
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
$sources = @(Get-JavaSources)
if ($sources.Count -eq 0) {
    throw 'No Java source files found.'
}

& $javac -d $OutputDir $sources
if ($LASTEXITCODE -ne 0) {
    throw "javac failed with exit code $LASTEXITCODE"
}

Write-Host "Compiled $($sources.Count) source files to $OutputDir"
