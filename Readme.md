# LinqArraylist

[ ![Download](https://api.bintray.com/packages/loukaspd/LinqArraylist/LinqArraylist/images/download.svg) ](https://bintray.com/loukaspd/LinqArraylist/LinqArraylist/_latestVersion)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-LinqArraylist-green.svg?style=flat )]( https://android-arsenal.com/details/1/7060 )  
use Linq-like functions to Java Collections


```
    implementation 'gr.loukaspd:LinqArraylist:{latest version}'
```
where `{latest version}` corresponds to published version in [ ![Download](https://api.bintray.com/packages/loukaspd/LinqArraylist/LinqArraylist/images/download.svg) ](https://bintray.com/loukaspd/LinqArraylist/LinqArraylist/_latestVersion)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-LinqArraylist-green.svg?style=flat )]( https://android-arsenal.com/details/1/7060 )

## Usage
1. Instantiate a LinqArrayList object by passing it your original Collection
2. Apply linq methods (even chaining) like select, where etc..
3. Get the resulting Collection by calling `getCollection` or `getArrayList()` to get a new ArrayList from the collection


## Examples

```java
public static void main(String[] args) {
        ArrayList<SampleClass> myList = new ArrayList<>();
        myList.add(new SampleClass(1, "red"));
        myList.add(new SampleClass(2, "green"));
        myList.add(new SampleClass(3, "blue"));


        ArrayList<SampleClass> result = new LinqArrayList<>(myList)
                .where(new LinqArrayList.BooleanFunc<SampleClass>() {
                    @Override
                    public boolean lambda(SampleClass item) {
                        return item.id > 1;
                    }
                })
                .getArrayList();
        System.out.println(result);   //[2: green, 3: blue]

        System.out.println(new LinqArrayList<>(myList)
                .firstOrDefault(new LinqArrayList.BooleanFunc<SampleClass>() {
                    @Override
                    public boolean lambda(SampleClass item) {
                        return item.color.equals("red");
                    }
                }));    //1: red


        // chaining
        System.out.println(
                new LinqArrayList<>(myList)
                .where(new LinqArrayList.BooleanFunc<SampleClass>() {
                    @Override
                    public boolean lambda(SampleClass item) {
                        return item.id > 1;
                    }
                })
                .select(new LinqArrayList.SelectFunc<SampleClass, String>() {
                    @Override
                    public String lambda(SampleClass item) {
                        return item.color;
                    }
                })
                .getArrayList()
        );          //[green, blue]
    }
```