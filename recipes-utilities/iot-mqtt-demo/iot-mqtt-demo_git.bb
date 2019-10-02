DESCRIPTION = "Sample application using python and MQTT for IOT demos."
HOMEPAGE = "https://mender.io"
LICENSE = "Apache-2.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = " \
        git://github.com/drewmoseley/iot-mqtt-bbb \
        file://iot-mqtt-actuator.service \
        file://iot-mqtt-sensor.service \
"
SRCREV = "${AUTOREV}"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit systemd

S = "${WORKDIR}/git"

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE_${PN} = "iot-mqtt-actuator.service iot-mqtt-sensor.service"

FILES_${PN} += " \
    ${systemd_unitdir}/system/iot-mqtt-actuator.service \
    ${systemd_unitdir}/system/iot-mqtt-sensor.service \
"

do_install() {
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/iot-mqtt-actuator.service ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/iot-mqtt-sensor.service ${D}/${systemd_unitdir}/system
  install -d ${D}/${bindir}/
  install -m 0755 ${S}/iot-mqtt-actuator.py ${D}/${bindir}/
  install -m 0755 ${S}/iot-mqtt-sensor.py ${D}/${bindir}/
}

RDEPENDS_${PN} += "python python-paho-mqtt"