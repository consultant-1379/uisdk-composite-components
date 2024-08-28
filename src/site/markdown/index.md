# Introduction

This is a library of components which can be used in testing an application developed using the UI SDK.

This is an inner-source project which anyone can contribute to. Please see the section on 
[contributing](contributing.html) for more information.

## Concept

A Component maps to an element on the UI.

A Composite Component maps to an element on the UI and contains components which map to its child elements on the UI.
The scope of mapping the child components to elements is confined to children of the parent element.

## Motivation

Simplify test code by creating a layer of abstraction which can be reused across teams.   
This has 2 benefits for the teams:

- Reduced test code. You don\'t need to design code to interact with child elements, just call the methods on the 
composite component.
- Reduced maintenance. If the UI changes the Composite Component will be updated externally, all you need to do is 
update the version.

## More info

More info on the Composite Component concept can be found in the 
[TAF documentation](http://taf.lmera.ericsson.se/taflanding/userdocs/Latest/index.html#_taf_ui_sdk)

## Verification

All Composite Components are tested against the examples from [CDS](https://cds.ericsson.se/#portal/presentation)

