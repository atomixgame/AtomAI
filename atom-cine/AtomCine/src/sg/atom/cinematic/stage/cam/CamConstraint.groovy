package sg.atom.cinematic.stage.cam

/**
 * A CamMove is like a steering behavior for camera but including the change in the perspective, angle and point of interest.
 * 
 * Constrainsts and behaviors (about spatial and geometric) share some common sense but should be distinguish. 
 * <ul>
 * <li>Constrainst have to be solved than be applied, behaviors almost nearly applied immediately.</li>
 * <li>Accumulate behaviors with blending weights is like relaxing constrainst.</li>
 * <li>Overview, constrainst solvers work over the constraint and change its status (mark as solved etc...).</li>
 * @author cuong.nguyenmanh2
 */
public abstract class CamConstraint {
    def style ="Normal"
    public abstract void change(cam);
    public abstract void update(cam);
}

