package sg.atom.ai.testbed.ailang.abl.generated;

import abl.runtime.*;
import wm.WME;
import java.util.*;
import java.lang.reflect.Method;
import abl.learning.*;
import sg.atom.ai.testbed.ailang.abl.wmes.*;
import sg.atom.ai.testbed.ailang.abl.actions.*;
import sg.atom.ai.testbed.ailang.abl.sensors.*;
public class ChaserAgent_Preconditions {
   static public boolean precondition0(int __$behaviorID, Object[] __$args, Hashtable __$variableTable, final BehavingEntity __$thisEntity) {
      switch (__$behaviorID) {
         case 1: {
            // fire_1
            int chaserX;
            int playerY;
            int playerX;
            int chaserY;
               List wmeList0;
               ListIterator wmeIter0;
               wmeList0 = BehavingEntity.getBehavingEntity().lookupWME("PlayerWME");
               wmeIter0 = wmeList0.listIterator();
               while(wmeIter0.hasNext()) {
                  PlayerWME wme__0 = (PlayerWME)wmeIter0.next();
                  if (
                     BehavingEntity.constantTrue(playerX = wme__0.getLocationX())
                     &&
                     BehavingEntity.constantTrue(playerY = wme__0.getLocationY())
                  )

                  {
                        List wmeList1;
                        ListIterator wmeIter1;
                        wmeList1 = BehavingEntity.getBehavingEntity().lookupWME("ChaserWME");
                        wmeIter1 = wmeList1.listIterator();
                        while(wmeIter1.hasNext()) {
                           ChaserWME wme__1 = (ChaserWME)wmeIter1.next();
                           if (
                              BehavingEntity.constantTrue(chaserX = wme__1.getLocationX())
                              &&
                              BehavingEntity.constantTrue(chaserY = wme__1.getLocationY())
                           )

                           {
                              __$variableTable.put("playerX", new Integer(playerX));
                              __$variableTable.put("chaserY", new Integer(chaserY));
                              __$variableTable.put("chaserX", new Integer(chaserX));
                              __$variableTable.put("playerY", new Integer(playerY));
                              return true;
                           }

                        }


                  }

               }


            return false;
         }
         case 3: {
            // move_1
            int playerY;
            int chaserY;
               List wmeList0;
               ListIterator wmeIter0;
               wmeList0 = BehavingEntity.getBehavingEntity().lookupWME("PlayerWME");
               wmeIter0 = wmeList0.listIterator();
               while(wmeIter0.hasNext()) {
                  PlayerWME wme__0 = (PlayerWME)wmeIter0.next();
                  if (
                     BehavingEntity.constantTrue(playerY = wme__0.getLocationY())
                  )

                  {
                        List wmeList1;
                        ListIterator wmeIter1;
                        wmeList1 = BehavingEntity.getBehavingEntity().lookupWME("ChaserWME");
                        wmeIter1 = wmeList1.listIterator();
                        while(wmeIter1.hasNext()) {
                           ChaserWME wme__1 = (ChaserWME)wmeIter1.next();
                           if (
                              BehavingEntity.constantTrue(chaserY = wme__1.getLocationY())
                           )

                           {
                                 if (
                                    chaserY >(playerY + ((ChaserAgent)__$thisEntity).ChaserSpeed)
                                 )

                                 {
                                    __$variableTable.put("chaserY", new Integer(chaserY));
                                    __$variableTable.put("playerY", new Integer(playerY));
                                    return true;
                                 }


                           }

                        }


                  }

               }


            return false;
         }
         case 4: {
            // move_2
            int playerY;
            int chaserY;
               List wmeList0;
               ListIterator wmeIter0;
               wmeList0 = BehavingEntity.getBehavingEntity().lookupWME("PlayerWME");
               wmeIter0 = wmeList0.listIterator();
               while(wmeIter0.hasNext()) {
                  PlayerWME wme__0 = (PlayerWME)wmeIter0.next();
                  if (
                     BehavingEntity.constantTrue(playerY = wme__0.getLocationY())
                  )

                  {
                        List wmeList1;
                        ListIterator wmeIter1;
                        wmeList1 = BehavingEntity.getBehavingEntity().lookupWME("ChaserWME");
                        wmeIter1 = wmeList1.listIterator();
                        while(wmeIter1.hasNext()) {
                           ChaserWME wme__1 = (ChaserWME)wmeIter1.next();
                           if (
                              BehavingEntity.constantTrue(chaserY = wme__1.getLocationY())
                           )

                           {
                                 if (
                                    chaserY <(playerY - ((ChaserAgent)__$thisEntity).ChaserSpeed)
                                 )

                                 {
                                    __$variableTable.put("chaserY", new Integer(chaserY));
                                    __$variableTable.put("playerY", new Integer(playerY));
                                    return true;
                                 }


                           }

                        }


                  }

               }


            return false;
         }
         case 5: {
            // move_3
            int chaserX;
            int playerX;
               List wmeList0;
               ListIterator wmeIter0;
               wmeList0 = BehavingEntity.getBehavingEntity().lookupWME("PlayerWME");
               wmeIter0 = wmeList0.listIterator();
               while(wmeIter0.hasNext()) {
                  PlayerWME wme__0 = (PlayerWME)wmeIter0.next();
                  if (
                     BehavingEntity.constantTrue(playerX = wme__0.getLocationX())
                  )

                  {
                        List wmeList1;
                        ListIterator wmeIter1;
                        wmeList1 = BehavingEntity.getBehavingEntity().lookupWME("ChaserWME");
                        wmeIter1 = wmeList1.listIterator();
                        while(wmeIter1.hasNext()) {
                           ChaserWME wme__1 = (ChaserWME)wmeIter1.next();
                           if (
                              BehavingEntity.constantTrue(chaserX = wme__1.getLocationX())
                           )

                           {
                                 if (
                                    chaserX >(playerX + ((ChaserAgent)__$thisEntity).ChaserSpeed)
                                 )

                                 {
                                    __$variableTable.put("playerX", new Integer(playerX));
                                    __$variableTable.put("chaserX", new Integer(chaserX));
                                    return true;
                                 }


                           }

                        }


                  }

               }


            return false;
         }
         case 6: {
            // move_4
            int chaserX;
            int playerX;
               List wmeList0;
               ListIterator wmeIter0;
               wmeList0 = BehavingEntity.getBehavingEntity().lookupWME("PlayerWME");
               wmeIter0 = wmeList0.listIterator();
               while(wmeIter0.hasNext()) {
                  PlayerWME wme__0 = (PlayerWME)wmeIter0.next();
                  if (
                     BehavingEntity.constantTrue(playerX = wme__0.getLocationX())
                  )

                  {
                        List wmeList1;
                        ListIterator wmeIter1;
                        wmeList1 = BehavingEntity.getBehavingEntity().lookupWME("ChaserWME");
                        wmeIter1 = wmeList1.listIterator();
                        while(wmeIter1.hasNext()) {
                           ChaserWME wme__1 = (ChaserWME)wmeIter1.next();
                           if (
                              BehavingEntity.constantTrue(chaserX = wme__1.getLocationX())
                           )

                           {
                                 if (
                                    chaserX <(playerX - ((ChaserAgent)__$thisEntity).ChaserSpeed)
                                 )

                                 {
                                    __$variableTable.put("playerX", new Integer(playerX));
                                    __$variableTable.put("chaserX", new Integer(chaserX));
                                    return true;
                                 }


                           }

                        }


                  }

               }


            return false;
         }
      default:
         throw new AblRuntimeError("Unexpected behaviorID " + __$behaviorID);
      }
   }
}
