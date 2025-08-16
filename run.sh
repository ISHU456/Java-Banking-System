#!/bin/bash

# Banking System Run Script
echo "🏦 Starting OOP Banking System Demo..."
echo "📚 This demo showcases all four pillars of Object-Oriented Programming:"
echo "   • Encapsulation"
echo "   • Inheritance" 
echo "   • Polymorphism"
echo "   • Abstraction"
echo ""

# Check if compiled
if [ ! -d "build" ] || [ ! -f "build/com/banking/demo/BankingSystemDemo.class" ]; then
    echo "⚠️  System not compiled. Running build script..."
    ./build.sh
    if [ $? -ne 0 ]; then
        echo "❌ Build failed!"
        exit 1
    fi
    echo ""
fi

# Run the demo
echo "🚀 Launching demo..."
java -cp build com.banking.demo.BankingSystemDemo
