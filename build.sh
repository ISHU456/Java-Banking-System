#!/bin/bash

# Banking System Build Script
echo "🏦 Building OOP Banking System..."

# Create build directory
mkdir -p build

# Compile all Java files
echo "📦 Compiling Java source files..."
javac -d build src/main/java/com/banking/*/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Run with: java -cp build com.banking.demo.BankingSystemDemo"
else
    echo "❌ Compilation failed!"
    exit 1
fi
