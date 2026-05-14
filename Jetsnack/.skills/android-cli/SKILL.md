---
name: android-cli
description: Orchestrates Android development tasks including project creation, deployment, SDK management, and environment diagnostics using the `android` command-line tool.
---
# Android CLI Specialist

This skill provides instructions for using the `android` CLI tool. The tool includes various commands for creating projects, running applications, interacting with devices, and managing the CLI environment.

## Sdk management
You can manage the installation of Android SDKs and tools using the `sdk` command for example

- `android sdk install <package>[@<version>]...`: Install specific packages. Multiple packages can be specified. `<version>` defaults to latest. e.g. `android sdk install "platforms;android-34"`
- `android sdk update [<pkg-name>]`: Update a specific package or all packages to the latest version.
- `android sdk remove <pkg-name>`: Remove a package from the local SDK.
- `android sdk list --all`: List installed and available SDK packages.

##  Project creation
You can create projects from templates using the `create` command.

For example: `android create empty-activity --output=./my-app`

## Doc searching
 
The `docs` command searches authoritative, high-quality Android developer documentation in the Android Knowledge Base.
By providing a few keywords, this tool will return high quality articles that contain examples or guidance on how to use Android APIs or libraries.
Use this tool to obtain additional information on how to achieve Android-specific tasks or to know more about Android APIs, surfaces, libraries or devices.

Always use this tool to get the most up-to-date information about Android concepts. Typical good use cases are:
  - Finding migration guides for APIs.
  - Finding examples for APIs.
  - Finding up-to-date information about Android APIs.
  - Finding best practices for Android concepts.

## Running APKs

Use the `run` command to run Android Application.

## Managing emulators
Manage Android Virtual Devices (AVDs) using the `android emuator` command

## Capturing screenshots
Capture an image of the current screen of a connected Android device and outputs it to a file using the `android screenshot` command.

## Managing skills
Manage antigravity agent skills for Android using the `android skills` command.

## Inspectuing UI Layouts
Using the `android layout` command you can inspect the UI layout of an Android application. It returns the layout tree of an Android application in JSON format. When debugging UI errors, this is often a much faster approach than taking a screenshot.

## Updating the CLI
Update the Android CLI using the `android update` command.

# `android help` output
Usage: android [-hV] [--sdk=PARAM] [COMMAND]
  -h, --help        Show this help message and exit.
      --sdk=PARAM   Path to the Android SDK
  -V, --version     Print version information and exit.
Commands:
  create    Create a new Android project
  run       Deploy an Android Application
  sdk       Download and list SDK packages
  skills    Manage skills
  update    Update the Android CLI
  info      Print environment information (SDK Location, etc.)
  docs      Android documentation commands
  layout    Returns the layout tree of an application
  screen    Commands to view the device
  emulator  Emulator commands
  init      Initializes the environment (eg. skills) for Android CLI.
  help      Shows the help of all commands

create    
          Usage: android create [-h] [--dry-run] [--verbose] [--name=applicationName]
                                [-o=dest-path] [template-name] [COMMAND]
          Create a new Android project
                [template-name]      The template name
                                       Default: empty-activity
                --dry-run            Execute the template but don't write to disk
                                       Default: false
            -h, --help               Show this help message and exit.
                --name=applicationName
                                     The name of the project (default is the name of the
                                       destination directory)
            -o, --output=dest-path   The destination project directory path
                --verbose            Enables verbose output
                                       Default: false
          Commands:
            list  List all available templates

run       
          Usage: android run [-hV] [--debug] [--activity=PARAM] [--device=PARAM]
                             [--type=PARAM] [--apks=PARAM[,PARAM...]]...
          Deploy an Android Application
                --activity=PARAM   The activity name
                --apks=PARAM[,PARAM...]
                                   The paths to the APKs
                --debug            Run in debug mode
                --device=PARAM     The device serial number
            -h, --help             Show this help message and exit.
                --type=PARAM       The component type (ACTIVITY, SERVICE, etc.)
            -V, --version          Print version information and exit.

sdk       
          Usage: android sdk [COMMAND]
          Download and list SDK packages
          Commands:
            install  Install SDK packages
            update   Update one or all packages to the latest version
            remove   Remove a package from the SDK
            list     List installed and available SDK packages

skills    
          Usage: android skills [COMMAND]
          Manage skills
          Commands:
            add     Install a skill
            remove  Remove a skill
            list    List available skills
            find    Find skills by keyword

update    
          Usage: android update [--url=PARAM]
          Update the Android CLI
                --url=PARAM   The URL to download the update from

info      
          Usage: android info
          Print environment information (SDK Location, etc.)

docs      
          Usage: android docs [-hV] [COMMAND]
          Android documentation commands
            -h, --help      Show this help message and exit.
            -V, --version   Print version information and exit.
          Commands:
            search  Search Android documentation
            fetch   Fetch Android documentation

layout    
          Usage: android layout [-dhpV] [-o=PARAM]
          Returns the layout tree of an application
            -d, --diff           Returns a flat list of the layout elements that have
                                   changed since the last invocation of ui-dump
            -h, --help           Show this help message and exit.
            -o, --output=PARAM   Writes the layout tree to the specified file or
                                   directory. If omitted, prints the tree to standard
                                   output
            -p, --pretty         Pretty-prints the returned JSON
            -V, --version        Print version information and exit.

screen    
          Usage: android screen [-hV] [COMMAND]
          Commands to view the device
            -h, --help      Show this help message and exit.
            -V, --version   Print version information and exit.
          Commands:
            capture  Outputs the device screen to a PNG
            resolve  Target UI elements visually

emulator  
          Usage: android emulator [-hV] [COMMAND]
          Emulator commands
            -h, --help      Show this help message and exit.
            -V, --version   Print version information and exit.
          Commands:
            create  Creates a virtual device
            start   Launches the specified virtual device
            stop    Stops the specified virtual device
            list    Lists available virtual devices

init      
          Usage: android init
          Initializes the environment (eg. skills) for Android CLI.

help      
          Usage: android help [COMMAND]
          Shows the help of all commands
                [COMMAND]   The command to show help for
