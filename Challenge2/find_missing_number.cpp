#include <iostream>
#include <vector>
using namespace std;

int findMissingNumber(const vector<int>& nums) {
    int n = nums.size();
    
    int sumMax = (n+1)*(n+2)/2;
    
    int sumMissing = 0;
    for (int i = 0; i < n; ++i) {
        sumMissing += nums[i];
    }
    
    return (sumMax - sumMissing);
}

int main() {
    int n;
    vector<int> nums;
    
    cout << "Enter the number of elements (n): ";
    cin >> n;
    cout << "Enter the elements of the array (n distinct numbers from 1 to " << n + 1 << "): " << endl;
    
    for (int i = 0; i < n; ++i) {
        int num;
        cin >> num;
        nums.push_back(num);
    }
    
    int missingNumber = findMissingNumber(nums);
    
    cout << "The missing number is: " << missingNumber << endl;
    return 0;
}

