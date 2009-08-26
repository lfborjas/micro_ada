/** Some valid java code from Eric Blake.  This should compile
 *  with any JSR-14 javac, as well as parse correctly (no syntax errors)
 *  using the java15.cup grammar in this package. */
class Eric15<T> {
    class B<S> { }
    Eric15<Integer>.B<Integer> c;
}
