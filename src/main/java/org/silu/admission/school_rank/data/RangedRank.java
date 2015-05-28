package org.silu.admission.school_rank.data;


public class RangedRank extends Rank {
  protected int head;
  protected int bottom;
  protected boolean isRanged;
  public RangedRank(int head, int bottom){
    super();
    this.head=head;
    this.bottom=bottom;
    isRanged=true;
  }
  public int getHead() {
    return head;
  }
  public void setHead(int head) {
    this.head = head;
  }
  public int getBottom() {
    return bottom;
  }
  public void setBottom(int bottom) {
    this.bottom = bottom;
  }
  public boolean isRanged() {
    return isRanged;
  }
  public void setRanged(boolean isRanged) {
    this.isRanged = isRanged;
  }
  @Override
  public String toString(){
    StringBuilder builder=new StringBuilder();
    builder.append(super.toString());
    builder.append("["+head+", "+bottom+"]");
    return builder.toString();
  }
}
