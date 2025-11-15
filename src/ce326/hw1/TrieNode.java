/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;

/**
 *
 * @author Panagiotis Tsatanis
 */
public class TrieNode {
    StringBuffer Word;
    boolean IsEndOfWord;
    TrieNode[] Children;
    TrieNode Parent;
    
    static final int ARRAYSIZE = 26;
    
    
    public TrieNode (String Element) {
        this.IsEndOfWord = true;
        this.Word = new StringBuffer(Element);
        this.Children = new TrieNode[ARRAYSIZE];
        for(int i = 0; i < ARRAYSIZE; i++) {
            this.Children[i] = null;
        }
        this.Parent = null;
    }
    
    public TrieNode() {
        this.IsEndOfWord = false;
        this.Word = new StringBuffer();
        this.Children = new TrieNode[ARRAYSIZE];
        for(int i = 0; i < ARRAYSIZE; i++) {
            this.Children[i] = null;
        }
        this.Parent = null;
    }

    
    boolean IsEndOfWord() {
        return(this.IsEndOfWord);
    }
    
    void setEndOfWord(boolean value) {
        this.IsEndOfWord = value;
    }
    
    char getLetter(int i) {
        return(this.Word.charAt(i));
    }
    
    StringBuffer getWord() {
        return(this.Word);
    }
    void setWord(String NewWord) {
        this.Word = null;
        this.Word = new StringBuffer(NewWord);
    }
    
    boolean IsSubstring(String S) {
        if( S.contains(this.Word) ) {
            return(true);
        }
        return(false);
    }
    
    String getSubstring(int StartIndex) {
        String S;
        S = new String( this.getWord() );
        return(S.substring(StartIndex));
    }
    
    int getWordLength() {
        return(this.Word.length());
    }
    
    TrieNode getIndexOfNode(int i) {
        return(this.Children[i]);
    }
    
    void setChild(int index,TrieNode Child) {
        this.Children[index] = Child;
    }
    
    int CountChildren() {
        int num = 0;
        for(int i = 0; i < Children.length; i++) {
            if( this.Children[i] != null ) {
                num++;
            }
        }
        return(num);
    }
    
    
    void MergeWithParent() {
        TrieNode ParentNode = this.getParent();
        if( ParentNode != null ) {
            StringBuffer NewWord;
            NewWord = new StringBuffer( ParentNode.getWord() );
            NewWord = NewWord.append(this.getWord());
            ParentNode.setWord(NewWord.substring(0, NewWord.length()));
            if( this.IsEndOfWord() == true ) {
                ParentNode.setEndOfWord(true);
            }
            this.setParent(null);
            ParentNode.setChild( ( this.getLetter(0) - 'a' ), null);
            for( int i = 0; i < 26; i++) {
                if ( this.getIndexOfNode(i) != null ) {
                    ParentNode.setChild( ( this.getIndexOfNode(i).getLetter(0) -'a' ) , this.getIndexOfNode(i));
                    this.getIndexOfNode(i).setParent(ParentNode);
                }
            }
           
        }
    }
    
    TrieNode getParent() {
       return(this.Parent);
    }
    
    void setParent(TrieNode NewParent) {
        this.Parent = NewParent;
    }
}
