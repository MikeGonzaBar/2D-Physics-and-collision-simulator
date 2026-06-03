param(
    [string] $ClassesDir = '',
    [string] $TestClassesDir = ''
)

$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\java-env.ps1"

$root = Get-ProjectRoot
$javac = Get-JavaTool 'javac'
$java = Get-JavaTool 'java'

if (-not $ClassesDir) {
    $ClassesDir = Join-Path $root 'build\classes'
}
if (-not $TestClassesDir) {
    $TestClassesDir = Join-Path $root 'build\test-classes'
}

& "$PSScriptRoot\build.ps1" -OutputDir $ClassesDir
New-Item -ItemType Directory -Force -Path $TestClassesDir | Out-Null

$testSources = @(Get-ChildItem -Recurse -Path (Join-Path $root 'test') -Filter '*.java' |
    ForEach-Object { $_.FullName })
if ($testSources.Count -eq 0) {
    throw 'No Java test source files found.'
}

& $javac -cp $ClassesDir -d $TestClassesDir $testSources
if ($LASTEXITCODE -ne 0) {
    throw "test javac failed with exit code $LASTEXITCODE"
}

& $java -cp "$ClassesDir;$TestClassesDir" ProyectoPOO.PhysicsSmokeTests
if ($LASTEXITCODE -ne 0) {
    throw "tests failed with exit code $LASTEXITCODE"
}
