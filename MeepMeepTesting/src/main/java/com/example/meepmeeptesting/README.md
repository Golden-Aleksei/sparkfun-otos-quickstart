# Converting between MeepMeepTesting and Roadrunner

 * Take coordinates from MeepMeepTesting to Roadrunner (x,y) ==> (y,x)

Actions conversions (for 90 degrees only):
 .forward(f) ==> .strafeTo(new Vector2d(x + f, y))
 .backward(b) ==> .strafeTo(new Vector2d(x - b, y))
 .waitSeconds(s) ==> .waitSeconds(s)
 .strafeLeft(l) ==> .strafeTo(new Vector2d(x, y - l))
 .strafeRight(r) ==> .strafeTo(new Vector2d(x, y + r))
 .lineTo(new Vector2d(a,b)) ==> .strafeTo(new Vector2d(b,a)

 .LineToLinearHeading(new Pose2d(a,b,Math.toRadians(c))) ==> .strafeToLinearHeading(new Vector2d(a,b), Math.toRadians(c))
 * Note: a is new x coordinate, b is new y coordinate, c is new heading in degrees
