void insert(unordered_map<string, vector<string> >& jumbleMap, const string&name){
    string *s = new string(name);
    jumbleMap[keyInput].push_back(name);
    // used to insert into the jumble hash map
}

void storeMap(unordered_map<string, vector<string> >& jumbleMap){

    while (file >> dictInput) {
    //
    keyInput = dictInput;
    ssort(keyInput);// sort the dictionary values
    wordCount++; // number of words is used to increment

    if (jumbleMap[keyInput].size() == 0) {
    classCount++; // if the size of the keyInput is greater than 0, increment by 1
    }

    jumbleMap[keyInput].push_back(dictInput); //push into the dictionary

    if (jumbleMap[keyInput].size() > largestWord) {
    largestWord = jumbleMap[keyInput].size();
    largestKey = keyInput;
    //is the size is greater than zero of the key class, than the largest word is the largest index in that chain
    }
    }
}

