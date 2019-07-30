package com.github.sioncheng.wrdcntr;

class BytesWord {

    public static final BytesWord EMPTY = new BytesWord();

    private byte[] data;
    private int pos;
    private int end;

    private int hash;
    private boolean hashChanged;

    private static final int DEFAULT_SIZE = 16;


    public BytesWord() {
        this.data = new byte[DEFAULT_SIZE];
        this.pos = 0;
        this.end = this.data.length;

        this.hash = 0;
        this.hashChanged = false;
    }

    public void append(byte b) {
        if (this.pos + 1 >= end) {
            byte[] newData = new byte[end + 15];
            for (int i =0; i < pos; i++) {
                newData[i] = this.data[i];
            }

            this.data = newData;
            end = this.data.length;
        }

        this.data[pos] = b;
        this.pos += 1;

        this.hashChanged = true;
    }

    public String toString() {
        if (this.pos == 0) {
            return null;
        } else {
            return new String(this.data, 0, this.pos);
        }
    }

    @Override
    public int hashCode() {
        if (this.pos == 0) {
            return super.hashCode();
        } else {
            if (hashChanged) {
                int h = 37;

                for (int i = 0; i < this.pos; i++) {
                    h = 31 * h + this.data[i];
                }

                this.hash = h;
                this.hashChanged = false;
            }

            return this.hash;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof BytesWord) {
            BytesWord that = (BytesWord)obj;
            if (that.pos == this.pos) {
                for (int i = 0; i < this.pos; i++) {
                    if (this.data[i] != that.data[i]) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
