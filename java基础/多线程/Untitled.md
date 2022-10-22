CAS: CompareAndSet  或者 CompareAndSwap 

```java
int i = 10;
while (true) {
    int oldValue = i;
    int newValue  = old + 1;
    if(oldValue == newValue) {
        i = newValue;
        return;
    }
}
```

