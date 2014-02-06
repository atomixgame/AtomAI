package sg.atom.ai.testbed.ailang.abl.generated;

import abl.runtime.*;
import wm.WME;
import java.util.*;
import java.lang.reflect.Method;
import abl.learning.*;
import sg.atom.ai.testbed.ailang.abl.wmes.*;
import sg.atom.ai.testbed.ailang.abl.actions.*;
import sg.atom.ai.testbed.ailang.abl.sensors.*;
public class ChaserAgent_PreconditionSensorFactories {
   static public SensorActivation[] preconditionSensorFactory0(int __$behaviorID) {
      switch (__$behaviorID) {
         case 1: {
               SensorActivation[] __$activationArray = {
                  new SensorActivation(new ChaserSensor(), null),
                  new SensorActivation(new PlayerSensor(), null)
               };

               return __$activationArray;

         }
         case 3: {
               SensorActivation[] __$activationArray = {
                  new SensorActivation(new ChaserSensor(), null),
                  new SensorActivation(new PlayerSensor(), null)
               };

               return __$activationArray;

         }
         case 4: {
               SensorActivation[] __$activationArray = {
                  new SensorActivation(new ChaserSensor(), null),
                  new SensorActivation(new PlayerSensor(), null)
               };

               return __$activationArray;

         }
         case 5: {
               SensorActivation[] __$activationArray = {
                  new SensorActivation(new ChaserSensor(), null),
                  new SensorActivation(new PlayerSensor(), null)
               };

               return __$activationArray;

         }
         case 6: {
               SensorActivation[] __$activationArray = {
                  new SensorActivation(new ChaserSensor(), null),
                  new SensorActivation(new PlayerSensor(), null)
               };

               return __$activationArray;

         }
      default:
         throw new AblRuntimeError("Unexpected behaviorID " + __$behaviorID);
      }
   }
}
