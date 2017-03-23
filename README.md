# Raycaster
In progress simple 2.5D raycaster.
###
Introduction:
####
Raycasting is the action of drawing vectors or rays in order to draw a 3D-like view with a 2D map. This implementation of it uses the Digital differential analyzer algorithm to continuously draw views.

## Changelog:

###
3/23/17
####
Added WASD support, can now move around the map. Will add some sort of custom map generation next

###
2/14/17
####
Fixed the problem with only drawing 2 lines. Will now correctly draw the map given a start position.
However, it's necessary to specify the initial position and direction vector within the code for a new view. Movement functionality
is not added yet.
###
2/4/17:
#####
Inital commit. Contains all of the original classes and files. Implemented with JFrame.
Issues: For some reason, for loop in main class only draws 2 lines. 3D-ish image is visible but area between lines aren't filled in.

