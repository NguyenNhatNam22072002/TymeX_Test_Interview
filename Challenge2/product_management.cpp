#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

using namespace std;

class Product {
public:
    string name;
    double price;
    int quantity;

    // Constructor
    Product(string n, double p, int q) : name(n), price(p), quantity(q) {}

    double getTotalValue() const {
        return price * quantity;
    }
};

// Comparison functions for sorting
bool compareByPriceDesc(const Product& a, const Product& b) {
    return a.price > b.price;
}

bool compareByPriceAsc(const Product& a, const Product& b) {
    return a.price < b.price;
}

bool compareByQuantityDesc(const Product& a, const Product& b) {
    return a.quantity > b.quantity;
}

bool compareByQuantityAsc(const Product& a, const Product& b) {
    return a.quantity < b.quantity;
}

int main() {
    // Product list
    vector<Product> inventory;
    inventory.push_back(Product("Laptop", 999.99, 5));
    inventory.push_back(Product("Smartphone", 499.99, 10));
    inventory.push_back(Product("Tablet", 299.99, 0));
    inventory.push_back(Product("Smartwatch", 199.99, 3));

    // Calculate total inventory value
    double totalValue = 0;
    for (size_t i = 0; i < inventory.size(); ++i) {
        totalValue += inventory[i].getTotalValue();
    }
    cout << "Total inventory value: " << totalValue << endl;

    // Find the most expensive product
    Product* mostExpensive = &inventory[0];
    for (size_t i = 1; i < inventory.size(); ++i) {
        if (inventory[i].price > mostExpensive->price) {
            mostExpensive = &inventory[i];
        }
    }
    cout << "Most expensive product: " << mostExpensive->name << endl;

    // Check if the product "Headphones" is in stock
    bool isHeadphonesInStock = false;
    for (size_t i = 0; i < inventory.size(); ++i) {
        if (inventory[i].name == "Headphones") {
            isHeadphonesInStock = true;
            break;
        }
    }
    cout << "Are headphones in stock? " << (isHeadphonesInStock ? "Yes" : "No") << endl;

    // Sort products by price in descending order
    sort(inventory.begin(), inventory.end(), compareByPriceDesc);
    cout << endl << "Products sorted by price (descending):" << endl;
    for (size_t i = 0; i < inventory.size(); ++i) {
        cout << inventory[i].name << ": " << inventory[i].price << " (Quantity: " << inventory[i].quantity << ")" << endl;
    }

    // Sort products by quantity in ascending order
    sort(inventory.begin(), inventory.end(), compareByQuantityAsc);
    cout << endl << "Products sorted by quantity (ascending):" << endl;
    for (size_t i = 0; i < inventory.size(); ++i) {
        cout << inventory[i].name << ": " << inventory[i].price << " (Quantity: " << inventory[i].quantity << ")" << endl;
    }

    return 0;
}

