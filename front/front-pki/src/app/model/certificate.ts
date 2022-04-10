import { KeyUsage } from "../model/keyUsage"

export interface Certificate{
    issuer : string,
    serialNumber: string
    country: string,
    organizationUnit: string,
    organization : string,
    surname: string,
    commonName : string,
    email: string,
    startDate: Date,
    endDate: Date,
    keyUsage: KeyUsage,
    certificateType : number 
}