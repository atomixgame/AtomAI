package sg.atom.cinematic.stage.cam.planer

/**
 * Constraint-Based Camera Planning
Once a cinematic goal indicating the characters of in-terest has b een passed to the cinematography planner,
the constraint formulator creates a constraint problem
by sp ecifying the set of characters of interest and the
preferred way in which eachcharacter is to b e viewed.
The cinematography planner accommo dates four typ es
of constraints, each of which can b e applied to anychar-acter, includes a relative strength, and a marker indicat-ing whether that constraint can b e relaxed. Subject in-clusion constraints sp ecify whichcharacters to include
in the camera's eld of view. Vantage angle constraints
indicate the p ermissible and optimal relative orienta-tions b etween the camera and the character. Shot dis-tance constraints sp ecify the minimum, maximum, and
optimal distances b etween the camera and a charac-ter. Finally, occlusion avoidance constraints indicate
whether the camera p osition should b e displaced from
the optimal vantage angle when necessary to prevent
the character from b eing o ccluded by obstacles.

<p>The cinematic constraint solver must identify a re-gion of space that is satisfactory to all constraints
within which it can p osition the camera. The cinematic
constraint algorithm (Figure 3) b egins by determining
the consistent region of 3D space in which the camera
can b e placed to satisfy each of the constraints (Step 1).
The critical step converts the consistent regions, which
are expressed as spatial relations relative to individual
sub jects, into a corresp onding representation in terms
of a common \global" comp osite spherical co ordinate
system with origin at the midp oint of all sub jects.

<p>If the intersection of the consistent regions I (Step 2)
is non-empty, then the cinematographer searches for
the spherical co ordinates p oint within I that is nearest
the optimal vantage for viewing the sub ject(s) (Step 3).
The camera distance from the aim p oint (central to the
sub ject ob jects) is computed via intersecting distance
intervals corresp onding to the consistent regions for the
viewing distance constraints. The solution p oint is then
converted from spherical co ordinates to Cartesian co or-dinates to determine the camera p osition. However, if
the intersection I is empty, then constraint relaxation
and p ossibly shot decomp osition metho ds (discussed
b elow) are employed to compute an alternate solution.
If no solution can b e found for the given constraint
problem, then the cinematography planner attempts
to nd an alternative (Steps 4, 5, and 6). The cine-matography planner rst identies the combinations of
constraints that are incompatible, then tries to nd a
maximal solution which satises as many of the higher
priority constraints as p ossible. Combinations of in-
 */
class CamConstraintSolver{
    def relax(constraint){
        
    }
    
    def toSphericalCoordinate(pos){
        
    }
    
    def toCoordinate(spos){
        
    }
    
    def buildIncompatibleGraph(){
        
    }
    
}
