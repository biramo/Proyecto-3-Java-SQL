$ErrorActionPreference = "Stop"

New-Item -ItemType Directory -Force -Path "out" | Out-Null

Get-ChildItem -Recurse "src\\java" -Filter "*.java" |
    ForEach-Object { $_.FullName } |
    Set-Content -Path "sources.txt"

cmd /c "javac -d out @sources.txt"

Write-Host "OK: Compilado en .\\out"
