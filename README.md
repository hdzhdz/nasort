#  TL;DR

Instead of sorting numbers in ASCII order like Java"s ***"Natural Order"*** (Lexicographic), this Natural Order sorts numbers in an ordering of strings in alphabetical order, except that multi-digit numbers are treated atomically, i.e., as if they were a single character.  

Inspired by [***javascript-natural-sort***](https://www.npmjs.com/package/javascript-natural-sort).

#### Features
- Numeric support ✅
- Float support ✅
- Float & decimal notation support ✅
- Scientific notation support ✅
- IP addresses ✅
- Filenames ✅
- Dates sorting support ❌
- Money ✅
- Movie Titles ✅
- Padded zeros' numbers come first ✅
- Case-sensitive/insensitive sorting ✅
- Reverse ✅

## Usage  

Initiate and use:

```java
Comparator<String> sensitiveComparator = new NASort(false);
String[] arr1 = new String[] { "A", "C", "E", "b", "d", "f" };
Arrays.sort(arr1, sensitiveComparator);
```  
  
To change case sensitivity:  

```java
Comparator<String> sensitiveComparator = new NASort(true);
Comparator<String> insensitiveComparator = new NASort(false);
```

## Examples  

Find more in the unit test.  

### Simple numerics

```java
{ "10", "9", "2", "1", "4" } -> { "1", "2", "4", "9","10" }
```

### Floats 

```java
{ "10.0401", "10.022", "10.042", "10.021999" } -> { "10.021999", "10.022", "10.0401", "10.042" }
```

### Float & decimal notation 

```java
{ "10.04f","10.039F","10.038d","10.037D" } -> { "10.037D","10.038d","10.039F","10.04f" }
```

### Scientific notation 

```java
{ "1.528535047e5","1.528535047e7","1.528535047e3" } -> { "1.528535047e3","1.528535047e5","1.528535047e7" }
```

### IP addresses

```java
{ "192.168.0.100","192.168.0.1","192.168.1.1" } -> { "192.168.0.1","192.168.0.100","192.168.1.1" }
```

### Filenames

```java
{ "car.mov","01alpha.sgi","001alpha.sgi","my.string_41299.tif" } -> { "001alpha.sgi","01alpha.sgi","car.mov","my.string_41299.tif"
```

### Dates 

```java
{ "10/12/2008","10/11/2008","10/11/2007","10/12/2007" } -> { "10/11/2007", "10/12/2007", "10/11/2008", "10/12/2008" }
```

### Money

```java
{ "$10002.00","$10001.02","$10001.01" } -> { "$10001.01","$10001.02","$10002.00" }
```

### Movie Titles

```java
{ "1 Title - The Big Lebowski","1 Title - Gattaca","1 Title - Last Picture Show" } -> { "1 Title - Gattaca","1 Title - Last Picture Show","1 Title - The Big Lebowski" }
```

### Padded Zeros

```java
{ "0001", "002", "001", "1" } -> { "0001", "001", "1", "002" }
```  

### Case Sensitive

```java
{ "A", "b", "C", "d", "E", "f" } -> { "A", "C", "E", "b", "d", "f" }
```

### Reverse

```java
{ "10.0401", "10.022", "10.042", "10.021999" } -> { "10.042", "10.0401", "10.022", "10.021999" }
{ "-1", "-2", "4", "-3", "0", "-5" } -> { "4", "0", "-1", "-2", "-3", "-5" }
```